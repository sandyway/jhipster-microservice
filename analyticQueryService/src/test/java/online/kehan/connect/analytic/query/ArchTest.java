package online.kehan.connect.analytic.query;

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
            .importPackages("online.kehan.connect.analytic.query");

        noClasses()
            .that()
                .resideInAnyPackage("online.kehan.connect.analytic.query.service..")
            .or()
                .resideInAnyPackage("online.kehan.connect.analytic.query.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..online.kehan.connect.analytic.query.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
