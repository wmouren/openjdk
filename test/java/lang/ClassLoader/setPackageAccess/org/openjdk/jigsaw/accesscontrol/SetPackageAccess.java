/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package org.openjdk.jigsaw.accesscontrol;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import org.openjdk.jigsaw.accesscontrol.internal.Secret;
import org.openjdk.jigsaw.accesscontrol.p1.C1;
import org.openjdk.jigsaw.accesscontrol.p2.C2;

public class SetPackageAccess {

    @BeforeClass
    public void setupAccess() {
        ClassLoader cl = SetPackageAccess.class.getClassLoader();

        ClassLoader[] loaders = { cl, cl };
        String[] pkgs = { "org.openjdk.jigsaw.accesscontrol",
                          "org.openjdk.jigsaw.accesscontrol.p1" };

        cl.setPackageAccess("org.openjdk.jigsaw.accesscontrol.internal", loaders, pkgs);
    }

    @Test
    public void testAllowed() {
        assertEquals(new C1().getSecret(), Secret.get());
    }

    @Test(expectedExceptions=IllegalAccessError.class)
    public void testDenied () {
        String s = new C2().getSecret();
    }
}
