package userstories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mascotapp.core.MascotApp;
import com.mascotapp.core.MascotAppCore;
import com.mascotapp.core.filter.ContentFoundPostFilter;
import com.mascotapp.core.filter.ContentLostPostFilter;
import com.mascotapp.core.filter.ContentPetPostFilter;
import com.mascotapp.core.service.matcher.ContentPostMatcher;
import com.mascotapp.core.service.socialNetwork.MockSocialNetwork;
import com.mascotapp.core.service.socialNetwork.SocialNetwork;
import com.mascotapp.core.service.socialNetwork.SocialNetworkInfo;

public class UserStory3 {
	
	private MascotApp mascotApp;
	
	@BeforeEach
	void setUp() {
		this.setUpMascotApp();
	}
	
	private void setUpMascotApp() {
		SocialNetwork mockSocialNetworkFacebook = new MockSocialNetwork(null, "Facebook");
		SocialNetwork mockSocialNetworkInstagram = new MockSocialNetwork(null, "Instagram");
        Set<SocialNetwork> socialNetworks = new HashSet<>();
        socialNetworks.add(mockSocialNetworkFacebook);
        socialNetworks.add(mockSocialNetworkInstagram);
        MascotAppCore core = new MascotAppCore(
            	socialNetworks, 
            	new ContentPostMatcher(), 
            	new ContentPetPostFilter(), 
            	new ContentFoundPostFilter(),
            	new ContentLostPostFilter()
            );
        mascotApp = new MascotApp(core);
	}
	
	private void checkHasTwoSocialNetworks() {
		int activeSocialNetworks = 0;
		Set<SocialNetworkInfo> socialNetworks = this.mascotApp.getSocialNetworks();
		for (SocialNetworkInfo socialNetworkInfo : socialNetworks) {
			if(socialNetworkInfo.isActive()) {
				assertTrue(socialNetworkInfo.getName().equals("Facebook") || 
						socialNetworkInfo.getName().equals("Instagram"));
				activeSocialNetworks++;
			}
		}
		assertEquals(2, activeSocialNetworks);
	}
	
	private void checkHasOneSocialNetwork() {
		int activeSocialNetworks = 0;
		Set<SocialNetworkInfo> socialNetworks = this.mascotApp.getSocialNetworks();
		for (SocialNetworkInfo socialNetworkInfo : socialNetworks) {
			if(socialNetworkInfo.isActive()) {
				assertEquals("Facebook", socialNetworkInfo.getName());
				activeSocialNetworks++;
			}
		}
		assertEquals(1, activeSocialNetworks);
	}
	
	@Test
	public void CA_1_Ninguna_Red_Social_Activa() {
		this.mascotApp.deactivateSocialNetwork("Facebook");
		this.mascotApp.deactivateSocialNetwork("Instagram");
		Set<SocialNetworkInfo> socialNetworks = this.mascotApp.getSocialNetworks();
		for (SocialNetworkInfo socialNetworkInfo : socialNetworks) {
			assertEquals(false, socialNetworkInfo.isActive());
		}
	}
	
	@Test
	public void CA_2_Una_Red_Social_Activa() {
		this.mascotApp.deactivateSocialNetwork("Instagram");
		this.checkHasOneSocialNetwork();
	}
	
	@Test
	public void CA_3_Multiples_Redes_Sociales_Activas() {
		checkHasTwoSocialNetworks();
	}
	
	@Test
	public void CA_4_Red_Social_Invalida() throws IllegalArgumentException {
		assertThrows(IllegalArgumentException.class, () -> this.mascotApp.activateSocialNetwork("Twitter")); 
	}
	
	@Test
	public void CA_5_Reactivar_Red_Social() {
		checkHasTwoSocialNetworks();
		this.mascotApp.deactivateSocialNetwork("Instagram");
		checkHasOneSocialNetwork();
		this.mascotApp.activateSocialNetwork("Instagram");
		checkHasTwoSocialNetworks();
	}
}
