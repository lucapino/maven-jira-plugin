<h1>Maven Jira Plugin</h1><br>

Maven plugin for accessing Atlassian Jira

[![][Build Status img]][Build Status]
[![][Coverage Status img]][Coverage Status]
[![][Dependency Status img]][Dependency Status]
[![][license img]][license]
[![][Maven Central img]][Maven Central]
[![][Javadocs img]][Javadocs]

Plugin documentation can be found at https://lucapino.github.io/jira-maven-plugin

Available goals:
================
* **create-new-jira-version** - creates a new JIRA version
* **generate-release-notes** - generates a release notes file based on a velocity template
* **release-jira-version** - releases a JIRA version
* **mail-release-notes** -  send announce mail with release note.
* **transition-issues** - transitions issue based on a JQL query 

Example plugin definition:
==========================
    <plugin>
        <groupId>com.github.lucapino</groupId>
        <artifactId>jira-maven-plugin</artifactId>
        <version>2.0.2</version>
        <configuration>
            <serverId>jira-server</serverId>
            <url>https://jira.example.org/jira/ </url>
        </configuration>
    </plugin>

Example _create-new-jira-version_ goal configuration:
-------------------------------------
    <configuration>
        <jiraProjectKey>JRA</jiraProjectKey>
        <developmentVersion>${project.version}</developmentVersion>
    </configuration>

Example _generate-release-notes_ goal configuration:
------------------------------------------
    <configuration>
        <jiraProjectKey>JRA</jiraProjectKey>
        <releaseVersion>${project.version}</releaseVersion>
    </configuration>

Example _release-jira-version_ goal configuration:
----------------------------------------
    <configuration>
        <jiraProjectKey>JRA</jiraProjectKey>
        <releaseVersion>${project.version}</releaseVersion>
    </configuration>

Example _mail-release-notes_ goal configuration:
----------------------------------------
    <configuration>
        <smtpHost>localhost</smtpHost>
        <smtpPort>25</smtpPort>
        <smtpUsername>user</smtpUsername>
        <smtpPassword>passwd</smtpPassword>
        <toAddresses>
            <toAddress>foo@bar.com</toAddress>
        </toAddresses>
        <ccAddresses>
            <ccAddress>bar@foo.com</ccAddress>
        </ccAddresses>
        <bccAddresses>
            <bccAddress>bar.foo@foo-bar.com</bccAddress>
        </bccAddresses>
        <fromDeveloperId>foo.bar</fromDeveloperId>
    </configuration>

    <developers>
        <developer>
            <id>foo.bar</id>
            <name>Foo Bar</name>
            <email>foo@bar.com</email>
        </developer>
    </developers>

Example _transition-issues_ goal configuration:
-------------------------------------------
    <configuration>
        <jiraProjectKey>JRA</jiraProjectKey>
        <releaseVersion>${project.version}</releaseVersion>
        <!-- Parameter 0 = Project Key, Parameter 1 = Fix version -->
        <jqlTemplate>project = ''{0}'' AND status in (Resolved) AND fixVersion = ''{1}''</jqlTemplate>
        <transition>Closed</transition>
    </configuration>

[Build Status]:https://travis-ci.org/lucapino/jira-maven-plugin
[Build Status img]:https://travis-ci.org/lucapino/jira-maven-plugin.svg?branch=master

[Coverage Status]:https://codecov.io/gh/lucapino/jira-maven-plugin
[Coverage Status img]:https://codecov.io/gh/lucapino/jira-maven-plugin/branch/master/graph/badge.svg

[Dependency Status]:https://snyk.io/test/github/lucapino/jira-maven-plugin
[Dependency Status img]:https://snyk.io/test/github/lucapino/jira-maven-plugin/badge.svg?style=flat

[license]:LICENSE
[license img]:https://img.shields.io/badge/license-Apache%202-blue.svg

[Maven Central]:https://maven-badges.herokuapp.com/maven-central/com.github.lucapino/jira-maven-plugin
[Maven Central img]:https://maven-badges.herokuapp.com/maven-central/com.github.lucapino/jira-maven-plugin/badge.svg

[Javadocs]:http://www.javadoc.io/doc/com.github.lucapino/jira-maven-plugin
[Javadocs img]:http://javadoc.io/badge/com.github.lucapino/jira-maven-plugin.svg
