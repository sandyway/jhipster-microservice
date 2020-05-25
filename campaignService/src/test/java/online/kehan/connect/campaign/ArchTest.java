package online.kehan.connect.campaign;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("online.kehan.connect.campaign");

        noClasses()
            .that()
                .resideInAnyPackage("online.kehan.connect.campaign.service..")
            .or()
                .resideInAnyPackage("online.kehan.connect.campaign.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..online.kehan.connect.campaign.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
