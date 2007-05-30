package org.gridsphere.services.core.customization;

/**
 * This service is used to determine where GridSphere should store it's variable settings. The path will default
 * to $home/.gridsphere of the user who started the container or it can be set via JNDI.
 */
public interface SettingsService {

    /**
     * Returns the path where gridsphere stores it's settings
     *
     * @return path
     */
    String getSettingsPath();

    /**
     * Returns the path for a specific file in the settings directory
     *
     * @param path relative path of the file
     * @return absolute path of the file
     */
    public String getRealSettingsPath(String path);

}
