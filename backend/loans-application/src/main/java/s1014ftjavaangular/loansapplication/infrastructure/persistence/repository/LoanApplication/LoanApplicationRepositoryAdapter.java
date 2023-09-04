package s1014ftjavaangular.loansapplication.infrastructure.persistence.repository.LoanApplication;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import s1014ftjavaangular.loansapplication.domain.model.entity.LoanApplication;
import s1014ftjavaangular.loansapplication.domain.repository.LoanApplicationRepository;
import s1014ftjavaangular.loansapplication.infrastructure.persistence.entities.LoanApplicationEntity;

@RequiredArgsConstructor
@Repository
public class LoanApplicationRepositoryAdapter implements LoanApplicationRepository {
    private final LoanApplicationJpaRepository jpaRepository;

    @Transactional
    @Override
    public String findLastLoanApplicationNumber(){
        var lastNumber = jpaRepository.findLastLoanApplicationNumber();
        return lastNumber.isEmpty() ? "" : lastNumber.get();
    }

    @Transactional
    @Override
    public Integer countOfInactiveOrAuditingLoanApplicatin(String identification) {
        if(identification == null) throw new IllegalArgumentException("Identification cannot be empty");

        return jpaRepository.countIncompleteOrAuditingStatusLoanApplication(identification);
    }

    @Transactional
    @Override
    public LoanApplication findById(String id) {
        if(id == null) throw new IllegalArgumentException("ID cannot be empty");

        var loan = jpaRepository.findById(id);

        if(loan.isEmpty()) throw new NotFoundException("Loan application with id " + id + " was not found.");

        var loanEntity = loan.get();

        var loanModel = loanEntity.entityToModel();

        return loanModel;
    }

    @Override
    public void saveLoanApplication(LoanApplication model) {
        if(model == null) throw new IllegalArgumentException("The request cannot be empty");

        jpaRepository.save(LoanApplicationEntity.modelToEntity(model));
    }

}
