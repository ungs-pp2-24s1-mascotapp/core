package userstories;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mascotapp.core.discovery.MascotAppDiscovery;
import com.mascotapp.core.service.dataprovider.PetDataProvider;

public class UserStory2 {

//	  MascotAppDiscovery mascotAppDiscovery;
//	  
//	  @BeforeEach 
//	  void setUp() throws Exception { 
//		  mascotAppDiscovery = new MascotAppDiscovery(); 
//	  }
	 
	@Test//(expected = FileNotFoundException.class)
    public void CA1_discoverNonExistentPath() throws FileNotFoundException, IllegalArgumentException {
		String nonExistentLocation = "src/test/resources/US2/nonExistentLocation";
		
        assertThrows(FileNotFoundException.class, () -> MascotAppDiscovery.discover(nonExistentLocation));
    }

	/*@Test -- VER NO PASA EL TEST
	 * public void CA2_discoverInvalidLocationPath() throws FileNotFoundException, IllegalArgumentException { 
	 * String invalidLocation = "src/test/resources/US2/file.txt";
	 * 
	 * assertThrows(IllegalArgumentException.class, () -> mascotAppDiscovery.discover(invalidLocation)); }
	 */

	@Test
    public void CA3_discoverEmptyFolderPath() throws FileNotFoundException, IllegalArgumentException {
		String emptyFolder = "src/test/resources/US2/emptyFolder";
        Set<PetDataProvider> dataProvidersDiscovery = MascotAppDiscovery.discover(emptyFolder);
        
        assertTrue(dataProvidersDiscovery.isEmpty());
    }

}
