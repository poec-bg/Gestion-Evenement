import models.Evenement;
import models.Invite;
import org.junit.Test;
import services.InviteService;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class InviteServiceTest {

    @Test
    public void testCreate_everythingWrong() {
        // Given
        String email = null;
        Evenement evenement = null;

        // When
        try {

           Invite invite = InviteService.get().createInvite(email,evenement);
            fail();
        } catch (Exception e) {
            // Then
            assertTrue(true);
        }
    }



}
