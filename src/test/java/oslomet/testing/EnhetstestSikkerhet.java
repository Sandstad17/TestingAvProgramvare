package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {

    @InjectMocks
    //Denne skal testes
    private Sikkerhet sjekk;

    @Mock
    //Denne skal Mock'es
    private BankRepository bankrepository;

    @Test
    public void test_Sjekklogginn_FeilPersonummer(){

        String feilpersonnummerTest = "1234567891113";
        String passord = "superpassord";

        String result = sjekk.sjekkLoggInn(feilpersonnummerTest,passord);

        assertEquals("Feil i personnummer", result);
    }

    @Test
    public void test_Sjekklogginn_FeilPassord(){
        String personnummer = "12345678910";
        String feilpassord = "SuperPassordSomErVeldigMyeLengreEnnDetSomErLov";

        String result = sjekk.sjekkLoggInn(personnummer, feilpassord);

        assertEquals("Feil i passord", result);
    }

    @Test
    public void testSjekklogginnOK(){
    }

    @Test
    public void testSjekklogginn_FeiliPersonnummerEllerPassord(){
    }

}
