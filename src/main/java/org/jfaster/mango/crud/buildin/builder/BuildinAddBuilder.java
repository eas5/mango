/*
 * Copyright 2014 mango.jfaster.org
 *
 * The Mango Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.jfaster.mango.crud.buildin.builder;

import org.jfaster.mango.util.Joiner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ash
 */
public class BuildinAddBuilder extends AbstractBuildinBuilder {

  private final static String SQL_TEMPLATE = "insert into #table(%s) values(%s)";

  private final List<String> properties;

  private final List<String> columns;

  public BuildinAddBuilder(String prop4AutoGenerated, List<String> props, List<String> cols) {
    properties = new ArrayList<>(props);
    columns = new ArrayList<>(cols);
    if (prop4AutoGenerated != null) {
      int index = props.indexOf(prop4AutoGenerated);
      if (index < 0) {
        throw new IllegalArgumentException("error prop4AutoGenerated id [" + prop4AutoGenerated + "]");
      }
      properties.remove(index);
      columns.remove(index);
    }
  }

  @Override
  public String buildSql() {
    String s1 = Joiner.on(", ").join(columns);
    List<String> cps = new ArrayList<>();
    for (String prop : properties) {
      cps.add(":" + prop);
    }
    String s2 = Joiner.on(", ").join(cps);
    return String.format(SQL_TEMPLATE, s1, s2);
  }

}
