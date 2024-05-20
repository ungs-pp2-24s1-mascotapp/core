package userstories;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.mascotapp.core.discovery.MascotAppDiscovery;
import com.mascotapp.core.service.socialNetwork.SocialNetwork;

public class UserStory2 {
	 
	@Test
    public void CA1_Ubicacion_inexistente() throws FileNotFoundException, IllegalArgumentException {
		String nonExistentLocationPath = "src/test/resources/US2/nonExistentLocation";
		
        assertThrows(FileNotFoundException.class, () -> MascotAppDiscovery.discover(nonExistentLocationPath));
    }

	@Test 
	  public void CA2_Ubicacion_invalida() throws FileNotFoundException, IllegalArgumentException { 
		String invalidLocationPath = "src/test/resources/US2/file.txt";

		assertThrows(IllegalArgumentException.class, () -> MascotAppDiscovery.discover(invalidLocationPath)); 
	  }

	@Test
    public void CA3_Carpeta_vacia() throws FileNotFoundException, IllegalArgumentException {
		String emptyFolderPath = "src/test/resources/US2/emptyFolder";
        Set<SocialNetwork> socialNetworksDiscovery = MascotAppDiscovery.discover(emptyFolderPath);
        
        assertTrue(socialNetworksDiscovery.isEmpty());
    }
	
	@Test
    public void CA4_No_es_una_red_social() throws FileNotFoundException, IllegalArgumentException {
		String notSocialNetworkPath = "src/test/resources/US2/notSocialNetwork";
        Set<SocialNetwork> socialNetworksDiscovery = MascotAppDiscovery.discover(notSocialNetworkPath);
        
        assertTrue(socialNetworksDiscovery.isEmpty());
    }
	
	/*
	@Test
    public void CA5_unica_red_social() throws FileNotFoundException, IllegalArgumentException {
		String singleSocialNetworkPath = "src/test/resources/US2/singleSocialNetwork/Facebook.jar";
        Set<SocialNetwork> socialNetworksDiscovery = MascotAppDiscovery.discover(singleSocialNetworkPath);
                  
        assertEquals(1, socialNetworksDiscovery.size());
        
        for (SocialNetwork elemento : socialNetworksDiscovery) {
        	assertTrue(elemento.getClass().getSimpleName().equals("Facebook"));
        }        	
    }

	@Test
    public void CA6_Multiples_redes_sociales() throws FileNotFoundException, IllegalArgumentException {
		String multipleSocialNetworksPath = "src/test/resources/US2/multipleSocialNetworks";
        Set<SocialNetwork> socialNetworksDiscovery = MascotAppDiscovery.discover(multipleSocialNetworksPath);
                  
        assertEquals(2, socialNetworksDiscovery.size());
                
        for (SocialNetwork elemento : socialNetworksDiscovery) {
        	assertTrue(elemento.getClass().getSimpleName().equals("Facebook") ||
        			   elemento.getClass().getSimpleName().equals("Instagram")); 
        }   	
    }
    */
}