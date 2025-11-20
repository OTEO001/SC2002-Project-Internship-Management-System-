package service;

import entity.*;
import repository.*;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService implements IStudentService {
    private IInternshipRepository internshipRepository;
    private IApplicationRepository applicationRepository;
    private IUserRepository userRepository;

    public StudentService(IInternshipRepository internshipRepository,
                         IApplicationRepository applicationRepository,
                         IUserRepository userRepository) {
        this.internshipRepository = internshipRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<InternshipOpportunity> getEligibleOpportunities(Student student) {
        List<InternshipOpportunity> visibleOpps = internshipRepository.findVisibleOpportunities();
        
        return visibleOpps.stream()
                .filter(opp -> checkLevelEligibilityInternal(student, opp))
                .filter(opp -> checkMajorMatchInternal(student, opp))
                .filter(opp -> opp.getFilledSlots() < opp.getTotalSlots())
                .collect(Collectors.toList());
    }

    @Override
    public Application applyForInternship(Student student, String opportunityId) {
        // Check max applications (3 pending)
        checkMaxApplications(student);

        // Get opportunity
        InternshipOpportunity opportunity = internshipRepository.findById(opportunityId);
        if (opportunity == null) {
            throw new RuntimeException("Internship opportunity not found");
        }

        // Validate eligibility
        validateApplicationEligibility(student, opportunity);

        // Check for duplicate application
        List<Application> existingApps = applicationRepository.findByStudent(student);
        boolean alreadyApplied = existingApps.stream()
                .anyMatch(app -> app.getOpportunity().getOpportunityId().equals(opportunityId));
        
        if (alreadyApplied) {
            throw new RuntimeException("You have already applied for this internship");
        }

        // Create application
        Application application = new Application(student, opportunity);
        applicationRepository.save(application);
        
        // Add to student's applications
        student.addApplication(application);
        opportunity.addApplication(application);
        
        return application;
    }

    @Override
    public List<Application> viewMyApplications(Student student) {
        return applicationRepository.findByStudent(student);
    }

    @Override
    public void acceptPlacement(Student student, String applicationId) {
        Application application = applicationRepository.findById(applicationId);
        
        if (application == null) {
            throw new RuntimeException("Application not found");
        }

        if (!application.getStudent().getUserId().equals(student.getUserId())) {
            throw new RuntimeException("This is not your application");
        }

        if (application.getStatus() != ApplicationStatus.APPROVED) {
            throw new RuntimeException("You can only accept approved applications");
        }

        if (student.getAcceptedInternship() != null) {
            throw new RuntimeException("You have already accepted an internship placement");
        }

        // Accept the placement
        application.setAccepted(true);
        application.getOpportunity().incrementFilledSlots();
        student.setAcceptedInternship(application.getOpportunity());
        applicationRepository.update(application);

        // Withdraw all other applications
        withdrawOtherApplications(student);
    }

    @Override
    public void withdrawApplication(Student student, String applicationId) {
        Application application = applicationRepository.findById(applicationId);
        
        if (application == null) {
            throw new RuntimeException("Application not found");
        }

        if (!application.getStudent().getUserId().equals(student.getUserId())) {
            throw new RuntimeException("This is not your application");
        }

        if (application.getStatus() == ApplicationStatus.WITHDRAWN) {
            throw new RuntimeException("Application already withdrawn");
        }

        application.setStatus(ApplicationStatus.WITHDRAWN);
        applicationRepository.update(application);
    }

    private void validateApplicationEligibility(Student student, InternshipOpportunity opportunity) {
        if (!opportunity.isVisible()) {
            throw new RuntimeException("This internship is not visible");
        }

        if (opportunity.getStatus() != OpportunityStatus.APPROVED) {
            throw new RuntimeException("This internship is not approved yet");
        }

        if (opportunity.getFilledSlots() >= opportunity.getTotalSlots()) {
            throw new RuntimeException("No slots available");
        }

        if (!checkLevelEligibilityInternal(student, opportunity)) {
            throw new RuntimeException("Year 1-2 students can only apply for BASIC level internships");
        }

        if (!checkMajorMatchInternal(student, opportunity)) {
            throw new RuntimeException("This internship prefers a different major");
        }
    }

    private void checkMaxApplications(Student student) {
        int pendingCount = applicationRepository.countByStudent(student);
        if (pendingCount >= 3) {
            throw new RuntimeException("You have reached the maximum of 3 pending/approved applications");
        }
    }

    private boolean checkLevelEligibilityInternal(Student student, InternshipOpportunity opportunity) {
        // Year 1-2 can only apply for BASIC
        if (student.getYearOfStudy() <= 2 && opportunity.getLevel() != InternshipLevel.BASIC) {
            return false;
        }
        return true;
    }

    private boolean checkMajorMatchInternal(Student student, InternshipOpportunity opportunity) {
        String preferredMajor = opportunity.getPreferredMajor();
        return preferredMajor == null || preferredMajor.isEmpty() || 
               preferredMajor.equalsIgnoreCase(student.getMajor());
    }

    private void withdrawOtherApplications(Student student) {
        List<Application> applications = applicationRepository.findByStudent(student);
        for (Application app : applications) {
            if (app.getStatus() == ApplicationStatus.PENDING || 
                app.getStatus() == ApplicationStatus.APPROVED) {
                if (!app.isAccepted()) {
                    app.setStatus(ApplicationStatus.WITHDRAWN);
                    applicationRepository.update(app);
                }
            }
        }
    }
}
