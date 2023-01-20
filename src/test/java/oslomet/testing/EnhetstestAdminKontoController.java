package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
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
public class EnhetstestAdminKontoController {

    @InjectMocks
    // Denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void test_hentAlleKontiLoggetinn() {

        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("105010123456");
        Mockito.when(repository.hentAlleKonti()).thenReturn(konti);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void test_hentAlleKontiIkkeLoggetinn(){

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void test_RegistrerKontoLoggetinn(){

        Konto nyKonto = new Konto();

        when(sjekk.loggetInn()).thenReturn("1234567890");
        Mockito.when(repository.registrerKonto(any(Konto.class))).thenReturn("Konto registrert");

        String result = adminKontoController.registrerKonto(nyKonto);

        assertEquals("Konto registrert", result);
    }

    @Test
    public void test_RegistrerKontoIkkeLoggetinn(){

        Konto nyKonto = new Konto();

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKontoController.registrerKonto(nyKonto);

        assertEquals(resultat, "Ikke innlogget");

    }

    @Test
    public void test_endreKontoLoggetinn(){

        Konto nyEndreKonto = new Konto();

        when(sjekk.loggetInn()).thenReturn("1234567890");
        Mockito.when(repository.endreKonto(any(Konto.class))).thenReturn("Konto endret");

        String result = adminKontoController.endreKonto(nyEndreKonto);

        assertEquals("Konto endret", result);
    }

    @Test
    public void test_endreKontoIkkeLoggetinn(){

        Konto nyEndreKonto = new Konto();

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKontoController.endreKonto(nyEndreKonto);

        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void test_slettKontoLoggetinn(){

        String kontonummer = "96282819";

        when(sjekk.loggetInn()).thenReturn("7294568890");
        Mockito.when(repository.slettKonto(anyString())).thenReturn("Konto slettet");

        String result = adminKontoController.slettKonto(kontonummer);

        assertEquals("Konto slettet", result);
    }

    @Test
    public void test_slettKontoIkkeLoggetinn(){

        String kontonummer = "38282216";

        when(sjekk.loggetInn()).thenReturn(null);

        String result = adminKontoController.slettKonto(kontonummer);

        assertEquals("Ikke innlogget", result);
    }
}
