package org.apache.maven.scm.provider.perforce.command.tag;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.maven.scm.ScmTestCase;

/**
 * @author <a href="mailto:evenisse@apache.org">Emmanuel Venisse </a>
 * @version $Id: PerforceChangeLogConsumerTest.java 331276 2005-11-07 15:04:54Z
 *          evenisse $
 */
public class PerforceTagConsumerTest
    extends ScmTestCase
{
    public void testGoodParse()
        throws Exception
    {
        File testFile = getTestFile( "src/test/resources/perforce/tag_good.txt" );

        PerforceTagConsumer consumer = new PerforceTagConsumer();

        FileInputStream fis = new FileInputStream( testFile );
        BufferedReader in = new BufferedReader( new InputStreamReader( fis ) );
        String s = in.readLine();
        while ( s != null )
        {
            consumer.consumeLine( s );
            s = in.readLine();
        }

        assertEquals( "", consumer.getOutput() );
        assertTrue( consumer.isSuccess() );
        List results = consumer.getTagged();
        assertEquals( "Wrong number of entries returned", 2, results.size() );
        String entry = (String) results.get( 0 );
        assertTrue( entry.endsWith( "pom.xml" ) );
    }

    public void testBadParse()
        throws Exception
    {
        File testFile = getTestFile( "src/test/resources/perforce/tag_bad.txt" );

        PerforceTagConsumer consumer = new PerforceTagConsumer();

        FileInputStream fis = new FileInputStream( testFile );
        BufferedReader in = new BufferedReader( new InputStreamReader( fis ) );
        String s = in.readLine();
        while ( s != null )
        {
            consumer.consumeLine( s );
            s = in.readLine();
        }

        assertFalse( consumer.isSuccess() );
        assertEquals( 67, consumer.getOutput().length() );
    }
}
