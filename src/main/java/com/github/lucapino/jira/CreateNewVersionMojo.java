/*
 * Copyright 2012 George Gastaldi
 * Copyright 2013-2017 Luca Tagliani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.lucapino.jira;

import com.atlassian.jira.rest.client.api.domain.Project;
import com.atlassian.jira.rest.client.api.domain.Version;
import com.atlassian.jira.rest.client.api.domain.input.VersionInput;
import com.github.lucapino.jira.helpers.RemoteVersionComparator;
import java.util.Comparator;
import org.apache.commons.lang.WordUtils;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Goal that creates a version in a JIRA project.
 *
 * @author George Gastaldi
 * @author Luca Tagliani
 */
@Mojo(name = "create-new-jira-version")
@Execute(goal = "create-new-jira-version", phase = LifecyclePhase.DEPLOY)
public class CreateNewVersionMojo extends AbstractJiraMojo {

    /**
     * Next Development Version
     */
    @Parameter(defaultValue = "${project.version}", required = true)
    String developmentVersion;
    /**
     * Final name
     */
    @Parameter(defaultValue = "${project.build.finalName}")
    String finalName;
    /**
     * Whether the final name is to be used for the version; defaults to false.
     */
    @Parameter(defaultValue = "false")
    boolean finalNameUsedForVersion;
    /**
     * Comparator for discovering the latest release
     */
    Comparator<Version> remoteVersionComparator = new RemoteVersionComparator();

    @Override
    public void doExecute() throws Exception {
        Log log = getLog();

        Project jiraProject = jiraClient.getRestClient().getProjectClient().getProject(jiraProjectKey).claim();
        Iterable<Version> versions = jiraProject.getVersions();
        String newDevVersion;

        if (finalNameUsedForVersion) {
            newDevVersion = finalName;
        } else {
            newDevVersion = developmentVersion;
        }

        // Removing -SNAPSHOT suffix for safety and sensible formatting
        newDevVersion = WordUtils.capitalize(newDevVersion.replace(
                "-SNAPSHOT", "").replace("-", " "));

        boolean versionExists = isVersionAlreadyPresent(versions, newDevVersion);

        if (!versionExists) {

            VersionInput newVersion = new VersionInput(jiraProjectKey, newDevVersion, null, null, false, false);
            log.debug("New Development version in JIRA is: " + newDevVersion);
            jiraClient.getRestClient().getVersionRestClient().createVersion(newVersion).claim();

            log.info("Version created in JIRA for project key "
                    + jiraProjectKey + " : " + newDevVersion);
        } else {
            log.warn(String.format(
                    "Version %s is already created in JIRA. Nothing to do.",
                    newDevVersion));
        }

    }

    /**
     * Check if version is already present on array
     *
     * @param versions
     * @param newDevVersion
     * @return
     */
    boolean isVersionAlreadyPresent(Iterable<Version> versions,
            String newDevVersion) {
        boolean versionExists = false;

        if (versions != null) {
            // Creating new Version (if not already created)
            for (Version remoteVersion : versions) {
                if (remoteVersion.getName().equalsIgnoreCase(newDevVersion)) {
                    versionExists = true;
                    break;
                }
            }
        }

        // existant
        return versionExists;
    }
}
