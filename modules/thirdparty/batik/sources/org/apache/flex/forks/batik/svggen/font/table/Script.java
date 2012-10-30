/*

   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package org.apache.flex.forks.batik.svggen.font.table;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author <a href="mailto:david@steadystate.co.uk">David Schweinsberg</a>
 * @version $Id: Script.java 475477 2006-11-15 22:44:28Z cam $
 */
public class Script {

    private int defaultLangSysOffset;
    private int langSysCount;
    private LangSysRecord[] langSysRecords;
    private LangSys defaultLangSys;
    private LangSys[] langSys;
    
    /** Creates new ScriptTable */
    protected Script(RandomAccessFile raf, int offset) throws IOException {
        raf.seek(offset);
        defaultLangSysOffset = raf.readUnsignedShort();
        langSysCount = raf.readUnsignedShort();
        if (langSysCount > 0) {
            langSysRecords = new LangSysRecord[langSysCount];
            for (int i = 0; i < langSysCount; i++) {
                langSysRecords[i] = new LangSysRecord(raf);
            }
        }

        // Read the LangSys tables
        if (langSysCount > 0) {
            langSys = new LangSys[langSysCount];
            for (int i = 0; i < langSysCount; i++) {
                raf.seek(offset + langSysRecords[i].getOffset());
                langSys[i] = new LangSys(raf);
            }
        }
        if (defaultLangSysOffset > 0) {
            raf.seek(offset + defaultLangSysOffset);
            defaultLangSys = new LangSys(raf);
        }
    }

    public LangSys getDefaultLangSys() {
        return defaultLangSys;
    }

}
