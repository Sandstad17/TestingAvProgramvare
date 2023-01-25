package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    private static final String code ="Test";
    @InjectMocks
    // Denne skal testes
    private AdminKundeController adminKundeController;

    @Mock
    AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;



    @Test
    public void test_hentAlleOk(){
        // arrange 
        Kunde kunde1 = new Kunde("12344512345", "Erik", "Eriksen", "osloveien",
                "1233", "Oslo", "123456789", "sol123");
        List<Kunde> kundeliste = new ArrayList<>();
        kundeliste.add(kunde1);


        when(sjekk.loggetInn()).thenReturn("12344512345"); // pass med alle mulige parametere her ...
        Mockito.when(repository.hentAlleKunder()).thenReturn(kundeliste);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertEquals(kundeliste, resultat);
    }

    @Test
    public void test_hentAlleFeil(){
        // arrage
        //Mockito.when(repository.hentAlleKunder()).thenReturn(null);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);
    }

    @Test
    public void test_lagreKundeOk(){
        // arrage
        Kunde kunde3 = new Kunde("26040187129", "Erik", "Olsen", "veien", "1234", "Oslo",
                "12345678", "Passord");

        when(sjekk.loggetInn()).thenReturn("x");
        Mockito.when(repository.registrerKunde((any(Kunde.class)))).thenReturn("OK");

        // act
        String resultat = adminKundeController.lagreKunde(kunde3); // husk å bruke denne Controlleren, ikke opprett en ny!

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void test_lagreKundeFeil(){
        // metode 2 feil
        Kunde kunde3 = new Kunde("26040187129", "Erik", "Olsen", "veien", "1234", "Oslo",
                "12345678", "Passord");

        //Mockito.when(repository.registrerKunde(kunde3)).thenReturn("Ikke logget inn"); //?

        String resultat = adminKundeController.lagreKunde(kunde3);

        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void test_endreOk(){
        // metode 3 OK
        Kunde kunde4 = new Kunde("123456 12345", "Erik", "Anderse ", "veien", "1234", "Oslo",
        "12345678", "heiheihei");
        when(sjekk.loggetInn()).thenReturn("x");
        Mockito.when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        String restulat = adminKundeController.endre(kunde4);
        assertEquals("OK", restulat);
    }
    @Test
    public void test_endreFeil(){
        // metode 3 feil
        Kunde kunde4 = new Kunde("123456 12345", "Erik", "Anderse ", "veien", "1234", "Oslo",
                "12345678", "heiheihei");

        //Mockito.when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Ikke logget inn");

        String restulat = adminKundeController.endre(kunde4);
        assertEquals("Ikke logget inn", restulat);
    }

    @Test
    public void test_slettOk(){
        // metode 4 OK
        Mockito.when(repository.slettKunde(anyString())).thenReturn("OK");

        when(sjekk.loggetInn()).thenReturn("x");
        String resultat = adminKundeController.slett("01010110523");
        assertEquals("OK", resultat);
    }
    @Test
    public void test_slettFeil(){
        // metode 4 feil
        //Mockito.when(repository.slettKunde(anyString())).thenReturn("Feil");

        String resultat = adminKundeController.slett("01010110523");
        assertEquals("Ikke logget inn", resultat);
    }
}