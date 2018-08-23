package com.makesrc.examples.reflection;
/* 
  Copyright (c) 2018 Kent Yang
  
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
 
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
 
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <https://www.gnu.org/licenses/>.
  limitations under the License.
*/

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

/**
 * A demo showing a simple plugin framework where classes are dynamically loaded from a plugin
 * directory, then instance created from it then if it implements the IPlugin interface, it will
 * execute the method defined in the contracted interface.  Also demonstrates how to load a JAR
 * file and reading contents of the manifest to determine the class that implements the interface.
 *
 * @author Kent Yang
 */
public class PluginDemo {

  private static final String HOMEPATH = System.getenv("HOMEPATH");
  private static final Path
      APP_PLUGIN_PATH = Paths.get(HOMEPATH, PluginDemo.class.getSimpleName(), "plugins");

  public static void main(String[] args) {

    List<Path> pluginList = getPluginList();
    displayPluginsToLoad(pluginList);

    for (Path p : pluginList) {
      URL url = mapPathToURL(p);
      Manifest manifest = getManifestFromJar(p);
      if (manifest != null) {
        String mainClass = manifest.getMainAttributes().getValue("Main-Class");
        System.out.println("Main class: " + mainClass);
        IPlugin plugin = getPlugin(url, mainClass);
        if (plugin != null) {
          plugin.display();
        }
      }
    }
  }

  private static List<Path> getPluginList() {
    List<Path> paths = null;
    try {
      if (!Files.exists(APP_PLUGIN_PATH)) {
        System.out.println("Created application plugin directory!");
        Files.createDirectories(APP_PLUGIN_PATH);
      }
      System.out.println("Found plugin directory: " + APP_PLUGIN_PATH);
      paths = Files.walk(APP_PLUGIN_PATH, 1)
          .filter(p -> p.toString().endsWith(".jar"))
          .collect(Collectors.toList());
    } catch (IOException e) {
      paths = new ArrayList<>();
    }
    return paths;
  }

  private static void displayPluginsToLoad(List<Path> pluginList) {
    if (pluginList.isEmpty()) {
      System.out.println("No plugins to laod.");
    } else {
      System.out.println("Plugins to load: ");
      pluginList.stream().forEach(System.out::println);
    }
  }

  private static Manifest getManifestFromJar(Path p) {
    Manifest manifest;

    try (
        JarInputStream jin = new JarInputStream(new BufferedInputStream(Files.newInputStream(p)))) {
      manifest = jin.getManifest();
    } catch (IOException e) {
      System.out.println("No manifest found for: " + p);
      manifest = null;
    }
    return manifest;
  }

  private static IPlugin getPlugin(URL url, String pluginClass) {

    URL[] urlArray = new URL[] {url};

    IPlugin plugin = null;
    try (URLClassLoader cl = new URLClassLoader(urlArray)) {
      if (cl != null) {
        URL[] urls = cl.getURLs();
        for (URL u : urls)
          System.out.println("URL: " + u);
        Class<?> clazz = Class.forName(pluginClass, true, cl);
        Object obj = clazz.getConstructor().newInstance();
        if (obj instanceof IPlugin) {
          plugin = (IPlugin) obj;
          System.out.println("Loaded class: " + plugin.getClass().getCanonicalName());
          System.out.println("instance of IPlugin: " + (plugin instanceof IPlugin));
        }
      }
    } catch (ClassNotFoundException | IOException | NoSuchMethodException | InstantiationException
        | InvocationTargetException | IllegalAccessException e) {
      plugin = null;
    }
    return plugin;
  }

  private static URL mapPathToURL(Path p) {
    URL url = null;
    try {
      url = new URL("file://" + p.toString());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return url;
  }
}
