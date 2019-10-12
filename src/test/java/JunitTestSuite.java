import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(

        {
                OnlineRegistrationTests.class,
                PremierLeagueTests.class,
                HomeTownTests.class
        }
)
public class JunitTestSuite {

}
