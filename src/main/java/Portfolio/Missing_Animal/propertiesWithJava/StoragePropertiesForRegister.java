package Portfolio.Missing_Animal.propertiesWithJava;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StoragePropertiesForRegister {

	/**
	 * Folder location for storing files
	 */
	private String location = "upload-dir-Register";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
