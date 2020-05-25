package online.kehan.connect.analytic.collection;

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
            .importPackages("online.kehan.connect.analytic.collection");

        noClasses()
            .that()
                .resideInAnyPackage("online.kehan.connect.analytic.collection.service..")
            .or()
                .resideInAnyPackage("online.kehan.connect.analytic.collection.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..online.kehan.connect.analytic.collection.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
