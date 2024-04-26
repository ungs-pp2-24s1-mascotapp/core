package userstories;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.mascotapp.core.discovery.MascotAppDiscovery;
import com.mascotapp.core.service.dataprovider.PetDataProvider;

public class UserStory2 {
	 
	@Test
    public void CA1_discoverNonExistentPath() throws FileNotFoundException, IllegalArgumentException {
		String nonExistentLocationPath = "src/test/resources/US2/nonExistentLocation";
		
        assertThrows(FileNotFoundException.class, () -> MascotAppDiscovery.discover(nonExistentLocationPath));
    }

	@Test 
	  public void CA2_discoverInvalidLocationPath() throws FileNotFoundException, IllegalArgumentException { 
		String invalidLocationPath = "src/test/resources/US2/file.txt";

		assertThrows(IllegalArgumentException.class, () -> MascotAppDiscovery.discover(invalidLocationPath)); 
	  }

	@Test
    public void CA3_discoverEmptyFolderPath() throws FileNotFoundException, IllegalArgumentException {
		String emptyFolderPath = "src/test/resources/US2/emptyFolder";
        Set<PetDataProvider> dataProvidersDiscovery = MascotAppDiscovery.discover(emptyFolderPath);
        
        assertTrue(dataProvidersDiscovery.isEmpty());
    }
	
	@Test
    public void CA4_discoverNotSocialNetworkPath() throws FileNotFoundException, IllegalArgumentException {
		String notSocialNetworkPath = "src/test/resources/US2/notSocialNetwork";
        Set<PetDataProvider> dataProvidersDiscovery = MascotAppDiscovery.discover(notSocialNetworkPath);
        
        assertTrue(dataProvidersDiscovery.isEmpty());
    }

	@Test
    public void CA5_discoverSingleSocialNetworkPath() throws FileNotFoundException, IllegalArgumentException {
		String singleSocialNetworkPath = "src/test/resources/US2/singleSocialNetwork/Facebook.jar";
        Set<PetDataProvider> dataProvidersDiscovery = MascotAppDiscovery.discover(singleSocialNetworkPath);
                  
        assertEquals(1, dataProvidersDiscovery.size());
        
        for (PetDataProvider elemento : dataProvidersDiscovery) {
        	assertTrue(elemento.getClass().getSimpleName().equals("Facebook"));
        }        	
    }

	@Test
    public void CA6_discoverMultipleSocialNetworksPath() throws FileNotFoundException, IllegalArgumentException {
		String multipleSocialNetworksPath = "src/test/resources/US2/multipleSocialNetworks";
        Set<PetDataProvider> dataProvidersDiscovery = MascotAppDiscovery.discover(multipleSocialNetworksPath);
                  
        assertEquals(2, dataProvidersDiscovery.size());
                
        for (PetDataProvider elemento : dataProvidersDiscovery) {
        	assertTrue(elemento.getClass().getSimpleName().equals("Facebook") ||
        			   elemento.getClass().getSimpleName().equals("Instagram")); 
        }   	
    }
}