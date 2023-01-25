package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {

    @InjectMocks
    //Denne skal testes
    private Sikkerhet sjekk;

    @Mock
    //Denne skal Mock'es
    private BankRepository bankrepository;

    @Mock
    //denne skal mocke´s
    private HttpSession session;


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
    public void test_SjekklogginnOK(){

        Map<String,Object> attributes = new HashMap<String,Object>();

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());

        String personnummer = "12345678910";
        String passord = "PassordSomErLov";

        assertEquals("OK", sjekk.sjekkLoggInn(personnummer, passord));
    }

    @Test
    public void test_Sjekklogginn_FeiliPersonnummerEllerPassord(){

        String personnummer = "12345678910928362891";
        String feilpassord = "SuperPassordSomErVeldigMyeLengreEnnDetSomErLov";

        String result = sjekk.sjekkLoggInn(personnummer, feilpassord);

        assertEquals("Feil i personnummer eller passord", result);

        // org.opentest4j.AssertionFailedError: expected: <Feil i personnummer eller passord> but was: <Feil i personnummer>
        //Expected :Feil i personnummer eller passord
        //Actual   :Feil i personnummer

        //Får denne feilmeldingen, må endres så unntaket nås.

    }

}
