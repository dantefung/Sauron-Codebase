/*
 * Copyright (c) 2004-2006 Ronsoft Technologies (http://ronsoft.com)
 * Contact Ron Hitchens (ron@ronsoft.com) with questions about this code.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * The use of the Apache License does not indicate that this project is
 * affiliated with the Apache Software Foundation.
 */

package com.dantefung.io.nio.nioserver;

/**
 * Created by IntelliJ IDEA.
 * User: ron
 * Date: Apr 6, 2006
 * Time: 12:11:09 PM
 */
public interface ChannelFacade
{
	InputQueue inputQueue();
	OutputQueue outputQueue();
	void setHandler (InputHandler handler);
	int getInterestOps();
	void modifyInterestOps (int opsToSet, int opsToReset);
}
