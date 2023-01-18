package oslomet.testing;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    @InjectMocks
    // Denne skal testes
    private AdminKundeController adminKundeController;
}