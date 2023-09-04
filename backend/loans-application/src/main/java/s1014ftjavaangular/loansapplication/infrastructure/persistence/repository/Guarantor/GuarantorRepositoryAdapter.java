package s1014ftjavaangular.loansapplication.infrastructure.persistence.repository.Guarantor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import s1014ftjavaangular.loansapplication.domain.model.entity.Guarantor;
import s1014ftjavaangular.loansapplication.domain.model.entity.LoanApplication;
import s1014ftjavaangular.loansapplication.domain.repository.GuarantorRepository;
import s1014ftjavaangular.loansapplication.infrastructure.persistence.entities.GuarantorEntity;
import s1014ftjavaangular.loansapplication.infrastructure.persistence.entities.LoanApplicationEntity;
import s1014ftjavaangular.loansapplication.infrastructure.persistence.repository.GeneralData.GeneralDataJpaRepository;
import s1014ftjavaangular.loansapplication.infrastructure.persistence.repository.LoanApplication.LoanApplicationJpaRepository;

@Repository
@RequiredArgsConstructor
public class GuarantorRepositoryAdapter implements GuarantorRepository {
    private final GuarantorJpaRepository jpaRepository;
    private final LoanApplicationJpaRepository loanApplicationJpaRepository;
    private final GeneralDataJpaRepository generalDataJpaRepository;

    @Override
    public void saveGuarantor(Guarantor model, LoanApplication loanApplication) {
        if(model == null) throw new IllegalArgumentException("The request cannot be empty");

        var loanApplicationEntity = LoanApplicationEntity.modelToEntity(loanApplication);
        var guarantorEntity = GuarantorEntity.modelToEntity.apply(model);
        guarantorEntity.setLoansApplication(loanApplicationEntity);
        loanApplicationEntity.setGuarantor(guarantorEntity);

        jpaRepository.save(guarantorEntity);
    }
}
