package oslomet.testing;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    // Denne skal testes
    private AdminKontoController adminKontoController;
}
