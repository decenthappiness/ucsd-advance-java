package com.makesrc.examples.networking;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Scanner;

/**
 * Simple exmaple showing how to use the URL class to pull data from the web;
 * specifically an RSS feed.
 *
 * @author Kent Yang
 */
public class GetStockNews {

  public static final String STOCK_NEWS_SERVER_URL =
      "http://articlefeeds.nasdaq.com/nasdaq/symbols";

  public static void main(String[] args) throws IOException {
    URL url = null;
    if (args.length == 0) {
      url = new URL(STOCK_NEWS_SERVER_URL + "?symbol=GOOG");
    } else {
      url = new URL(STOCK_NEWS_SERVER_URL + "?symbol=" + args[0]);
    }

    InputStream is = url.openStream();
    Scanner in = new Scanner(is);
    while (in.hasNextLine()) {
      System.out.println(in.nextLine());
    }
  }
}
