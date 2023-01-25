package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentTransaksjoner_LoggetInn() {
        // arrange
        List<Transaksjon> listeTransaksjoner = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(983737, "8383728", 1000.00, "18.01.2023", "Transaksjon vellykket", "Vent", "393948429428");
        Transaksjon transaksjon2 = new Transaksjon(983537, "8383728", 200.00, "05.01.2023", "Transaksjon vellykket", "Vent", "33838383237");
        listeTransaksjoner.add(transaksjon1);
        listeTransaksjoner.add(transaksjon2);

        Konto enKonto = new Konto("233939393", "545454", 720, "Brukskonto", "NOK", listeTransaksjoner);

        when(sjekk.loggetInn()).thenReturn("393948429428");

        when(repository.hentTransaksjoner(anyString(),anyString(), anyString())).thenReturn(enKonto);

        // act
        Konto resultat = repository.hentTransaksjoner(anyString(),anyString(), anyString());

        // assert
        assertEquals(resultat, enKonto);
        assertEquals(resultat.getTransaksjoner(), listeTransaksjoner);
    }

    @Test
    public void hentTransaksjoner_IkkeLoggetInn() {
        //arrange
        when (sjekk.loggetInn()).thenReturn(null);

        //act
        Konto resultat = bankController.hentTransaksjoner(null, null, null);

        //assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldi_LoggetInn() {
        //arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }

    @Test
    public void registrerBetaling_LoggetInn() {
        // arrange
        Transaksjon enTransaksjon = new Transaksjon();

        when(sjekk.loggetInn()).thenReturn("2944748339");
        Mockito.when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("Betaling registrert");

        // act
        String resultat = bankController.registrerBetaling(enTransaksjon);

        //assert
        assertEquals("Betaling registrert", resultat);
    }

    @Test
    public void registerBetaling_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = bankController.registrerBetaling(null);

        assertNull(resultat, "Logg inn for å registrere betaling");
    }

    @Test
    public void hentBetalinger_LoggetInn() {

        List<Transaksjon> listeTransaksjoner = new ArrayList<>();

        when(sjekk.loggetInn()).thenReturn("483848348");
        Mockito.when(repository.hentBetalinger("483848348")).thenReturn(listeTransaksjoner);

        List <Transaksjon> resultat = bankController.hentBetalinger();

        assertEquals(listeTransaksjoner, resultat);
    }

    @Test
    public void hentBetalinger_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);

        List <Transaksjon> resultat = bankController.hentBetalinger();

        assertNull(resultat);
    }

    @Test
    public void utforBetaling_LoggetInn () {
        List <Transaksjon> listeTransaksjoner = new ArrayList<>();

        when(sjekk.loggetInn()).thenReturn("483848348");
        Mockito.when(repository.utforBetaling(383838383)).thenReturn("OK");

        Mockito.when(repository.hentBetalinger(anyString())).thenReturn(listeTransaksjoner);

        List <Transaksjon> resultat = bankController.utforBetaling(383838383);

        assertEquals(listeTransaksjoner, resultat);
    }

    @Test
    public void utforBetaling_IkkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);

        List <Transaksjon> resultat = bankController.hentBetalinger();

        assertNull(resultat);
    }

    @Test
    public void endre_LoggetInn () {
        Kunde enKunde = new Kunde();

        when(sjekk.loggetInn()).thenReturn("83383923");
        Mockito.when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Kunde info endret");

        String resultat = bankController.endre(enKunde);

        assertEquals("Kunde info endret", resultat);
    }

    @Test
    public void endre_IkkeLoggetInn() {
        Kunde enKunde = new Kunde();

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = bankController.endre(enKunde);

        assertNull(resultat);
    }
}

