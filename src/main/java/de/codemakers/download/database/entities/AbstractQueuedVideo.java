/*
 *    Copyright 2019 - 2020 Paul Hagedorn (Panzer1119)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package de.codemakers.download.database.entities;

import de.codemakers.download.YouTubeDL;
import de.codemakers.download.database.AbstractDatabase;
import de.codemakers.download.util.StringResolver;
import de.codemakers.io.file.AdvancedFile;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

public abstract class AbstractQueuedVideo<T extends AbstractQueuedVideo, D extends AbstractDatabase, V extends AbstractVideo> extends AbstractEntity<T, D> {
    
    protected int id = -1;
    protected String videoId = null;
    protected int priority = Integer.MIN_VALUE;
    protected Instant requested = null;
    protected String arguments = null;
    protected String configFile = null;
    protected String outputDirectory = null;
    //
    protected transient String configFileResolved = null;
    protected transient String outputDirectoryResolved = null;
    
    public AbstractQueuedVideo() {
        super();
    }
    
    public AbstractQueuedVideo(int id, String videoId, int priority, Timestamp requested, String arguments, String configFile, String outputDirectory) {
        this(id, videoId, priority, requested.toInstant(), arguments, configFile, outputDirectory);
    }
    
    public AbstractQueuedVideo(int id, String videoId, int priority, Instant requested, String arguments, String configFile, String outputDirectory) {
        super();
        this.id = id;
        this.videoId = videoId;
        this.priority = priority;
        this.requested = requested;
        this.arguments = arguments;
        this.configFile = configFile;
        this.outputDirectory = outputDirectory;
    }
    
    public int getId() {
        return id;
    }
    
    public T setId(int id) {
        this.id = id;
        return (T) this;
    }
    
    public String getVideoId() {
        return videoId;
    }
    
    public T setVideoId(String videoId) {
        this.videoId = videoId;
        return (T) this;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public T setPriority(int priority) {
        this.priority = priority;
        return (T) this;
    }
    
    public Timestamp getRequestedAsTimestamp() {
        final Instant requested = getRequested();
        if (requested == null) {
            return null;
        }
        return Timestamp.from(requested);
    }
    
    public Instant getRequested() {
        return requested;
    }
    
    public T setRequested(Instant requested) {
        this.requested = requested;
        return (T) this;
    }
    
    public String getArgumentsOrEmptyString() {
        final String arguments = getArguments();
        if (arguments == null) {
            return "";
        }
        return " " + arguments;
    }
    
    public String getArguments() {
        return arguments;
    }
    
    public T setArguments(String arguments) {
        this.arguments = arguments;
        return (T) this;
    }
    
    public boolean resolveStrings(StringResolver stringResolver) {
        Objects.requireNonNull(stringResolver, "stringResolver");
        if (configFile == null) {
            configFileResolved = null;
        } else {
            configFileResolved = stringResolver.resolve(configFile);
        }
        if (outputDirectory == null) {
            outputDirectoryResolved = null;
        } else {
            outputDirectoryResolved = stringResolver.resolve(outputDirectory);
        }
        //FIXME Initiate the downloading into the temp folder if the paths could not be resolved
        return ((configFile == null) == (configFileResolved == null)) && ((outputDirectory == null) == (outputDirectoryResolved == null));
    }
    
    public AdvancedFile getResolvedConfigFile() {
        if (configFile == null) {
            return YouTubeDL.getConfigFile(); //Default Config File
        }
        if (configFileResolved == null) {
            return null;
        }
        return new AdvancedFile(configFileResolved);
    }
    
    public String getConfigFile() {
        return configFile;
    }
    
    public T setConfigFile(String configFile) {
        this.configFile = configFile;
        return (T) this;
    }
    
    public AdvancedFile getResolvedOutputDirectory() {
        if (outputDirectory == null) {
            //FIXME What todo if there has never been an output directory set? (auto detect it with the file type?)
            return null;
        }
        if (outputDirectoryResolved == null) {
            return null;
        }
        return new AdvancedFile(outputDirectoryResolved);
    }
    
    public String getOutputDirectory() {
        return outputDirectory;
    }
    
    public T setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
        return (T) this;
    }
    
    public V asVideo() {
        return (V) useDatabaseOrNull((database) -> database.getVideoByVideoId(getVideoId()));
    }
    
    @Override
    protected T getFromDatabase() {
        return (T) useDatabaseOrNull((database) -> database.getQueuedVideoById(getId()));
    }
    
    @Override
    public boolean save() {
        return useDatabaseOrFalse((database) -> database.setQueuedVideoById(this, getId()));
    }
    
    @Override
    public String toString() {
        return "AbstractQueuedVideo{" + "id=" + id + ", videoId='" + videoId + '\'' + ", priority=" + priority + ", requested=" + requested + ", arguments='" + arguments + '\'' + ", configFile='" + configFile + '\'' + ", outputDirectory='" + outputDirectory + '\'' + ", configFileResolved='" + configFileResolved + '\'' + ", outputDirectoryResolved='" + outputDirectoryResolved + '\'' + '}';
    }
    
}
