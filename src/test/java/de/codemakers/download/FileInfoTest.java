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

package de.codemakers.download;

public class FileInfoTest {
    
    /*
    @Deprecated
    public static void main(String[] args) throws Exception {
        Logger.getDefaultAdvancedLeveledLogger().setMinimumLogLevel(LogLevel.FINE);
        final String url = args[0];
        Logger.log("url=" + url);
        final Source source = YouTubeSource.ofString(url);
        Logger.log("source=" + source);
        final long start = System.currentTimeMillis();
        final Doublet<List<FileInfo>, Future<List<FileInfo>>> doublet = YouTubeDL.downloadFileInfosFromListAndThenAsync(source, true);
        //Logger.log("doublet=" + doublet);
        //Logger.log("doublet.getA()=" + doublet.getA());
        Logger.log(doublet.getA().stream().map(Objects::toString).collect(Collectors.joining("\n\n", "\n\n\n\nBEFORE:\n", "--- END ---\n\n\n\n")));
        final Future<List<FileInfo>> future = doublet.getB();
        Logger.log("future=" + future);
        final List<FileInfo> fileInfos = future.get();
        //Logger.log("fileInfos=" + fileInfos);
        Logger.log(fileInfos.stream().map(Objects::toString).collect(Collectors.joining("\n\n", "\n\n\n\nAFTER:\n", "--- END ---\n\n\n\n")));
        final long duration = System.currentTimeMillis() - start;
        Logger.log("Time taken: " + (duration / 1000) + " seconds");
    }
    */
    
}
