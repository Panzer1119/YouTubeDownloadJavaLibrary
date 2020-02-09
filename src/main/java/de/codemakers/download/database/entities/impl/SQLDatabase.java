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

package de.codemakers.download.database.entities.impl;

import de.codemakers.download.database.entities.AbstractDatabase;

import java.util.List;

public class SQLDatabase extends AbstractDatabase<SQLDatabase, MediaFile, ExtraFile, YouTubeVideo, YouTubePlaylist> {
    
    @Override
    public YouTubeVideo getVideoByVideoId(String videoId) {
        return null; //TODO
    }
    
    @Override
    public List<YouTubeVideo> getVideosByPlaylistId(String playlistId) {
        return null; //TODO
    }
    
    @Override
    public List<String> getVideoIdsByPlaylistId(String playlistId) {
        return null; //TODO
    }
    
    @Override
    public YouTubePlaylist getPlaylistByPlaylistId(String playlistId) {
        return null; //TODO
    }
    
    @Override
    public List<YouTubePlaylist> getPlaylistsByVideoId(String videoId) {
        return null; //TODO
    }
    
    @Override
    public List<String> getPlaylistIdsByVideoId(String videoId) {
        return null; //TODO
    }
    
    @Override
    public boolean playlistContainsVideo(String playlistId, String videoId) {
        return false; //TODO
    }
    
    @Override
    public List<MediaFile> getMediaFilesByVideoId(String videoId) {
        return null; //TODO
    }
    
    @Override
    public List<ExtraFile> getExtraFilesByVideoId(String videoId) {
        return null; //TODO
    }
    
}
