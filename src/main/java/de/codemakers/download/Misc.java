/*
 *    Copyright 2019 Paul Hagedorn (Panzer1119)
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

import de.codemakers.base.logger.Logger;
import de.codemakers.base.util.tough.ToughConsumer;
import de.codemakers.io.file.AdvancedFile;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.time.Duration;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Misc {
    
    private static final Pattern PATTERN_DOWNLOAD_PROGRESS = Pattern.compile("\\[download\\] +(\\d+(?:,|.\\d*)?)% of [0-9.,]+[a-zA-Z]+ at [0-9.,]+[a-zA-Z]+\\/s ETA.*");
    
    public static int monitorProcess(Process process, DownloadProgress downloadProgress) {
        Objects.requireNonNull(downloadProgress, "downloadProgress");
        final int exitValue = monitorProcess(process, (normal) -> {
            //System.out.println("[INFO ]: " + normal); //TODO Debug only
            final Matcher matcher = PATTERN_DOWNLOAD_PROGRESS.matcher(normal);
            if (matcher.matches()) {
                final int index = downloadProgress.getNextProgressIndex();
                if (index == -1) {
                    System.err.println("Index == -1 ?!" + ", rejected: \"" + normal + "\""); //TODO Debug only
                    return;
                }
                final float progress = Float.parseFloat(matcher.group(1)) / 100.0F;
                downloadProgress.setProgress(index, progress);
                //System.out.println(String.format("Progress [%d]: %f", index, progress)); //TODO Debug only
            }
        }, (error) -> {
            System.err.println("[ERROR]: " + error); //TODO Debug only
        });
        return exitValue;
    }
    
    public static int monitorProcessDefault(Process process) {
        return monitorProcessNothing(process);
    }
    
    public static int monitorProcessToFile(Process process, AdvancedFile output) {
        return monitorProcessToFile(process, output, true);
    }
    
    public static int monitorProcessToFile(Process process, AdvancedFile output, boolean append) {
        if (output == null) {
            return monitorProcessNothing(process);
        }
        try (final BufferedWriter bufferedWriter = output.createBufferedWriter(append)) {
            final int exitValue = monitorProcess(process, (normal) -> {
                bufferedWriter.write("[INFO ]: " + normal);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }, (error) -> {
                bufferedWriter.write("[ERROR]: " + error);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            });
            bufferedWriter.flush();
            return exitValue;
        } catch (Exception ex) {
            Logger.handleError(ex);
            return -1;
        }
    }
    
    public static int monitorProcessNothing(Process process) {
        return monitorProcess(process, null, null);
    }
    
    public static int monitorProcessDebug(Process process) {
        return monitorProcess(process, System.out::println, System.err::println);
    }
    
    public static int monitorProcess(Process process, ToughConsumer<String> normal) {
        return monitorProcess(process, normal, normal);
    }
    
    public static int monitorProcess(Process process, ToughConsumer<String> normal, ToughConsumer<String> error) {
        final Thread thread_normal = new Thread(() -> monitorInputStream(process.getInputStream(), normal));
        thread_normal.start();
        final Thread thread_error = new Thread(() -> monitorInputStream(process.getErrorStream(), error));
        thread_error.start();
        try {
            thread_normal.join();
            thread_error.join();
        } catch (Exception ex) {
        }
        try {
            return process.waitFor();
        } catch (Exception ex) {
            return -1;
        }
    }
    
    protected static void monitorInputStream(InputStream inputStream, ToughConsumer<String> toughConsumer) {
        final Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            if (toughConsumer != null) {
                toughConsumer.acceptWithoutException(line);
            }
        }
        scanner.close();
    }
    
    public static String durationToString(Duration duration) {
        if (duration.toHours() > 0) {
            return String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutes() % 60, duration.getSeconds() % 60);
        } else if (duration.toMinutes() > 0) {
            return String.format("%02d:%02d", duration.toMinutes(), duration.getSeconds() % 60);
        } else if (duration.getSeconds() > 9) {
            return "" + duration.getSeconds();
        }
        return "0" + duration.getSeconds();
    }
    
    public static Duration stringToDuration(String duration) {
        final String[] split = duration.split(":");
        Duration duration_ = Duration.ZERO;
        if (split.length > 0) {
            duration_ = duration_.plusSeconds(Long.parseLong(split[split.length - 1]));
        }
        if (split.length > 1) {
            duration_ = duration_.plusMinutes(Long.parseLong(split[split.length - 2]));
        }
        if (split.length > 2) {
            duration_ = duration_.plusHours(Long.parseLong(split[split.length - 3]));
        }
        return duration_;
    }
    
}
