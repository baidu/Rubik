/**
 * Copyright (C) Baidu Inc. All rights reserved.
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
package com.rubik.router

import com.rubik.builder.router.RouterBuildable
import com.rubik.router.annotations.RInvariant

/**
 * The result callback of RouterBuildable, with 1 parameter.
 *
 * @see com.rubik.builder.result.ResultsBuildable
 *
 * @since 1.6
 */
@RInvariant
inline fun <reified R1> RouterBuildable.result(
    noinline onReceive: (R1) -> Unit
) =
    receiveResults { results ->
        onReceive(
            results.toTypeOfT(0)
        )
    }

/**
 * The result callback of RouterBuildable, with 2 parameter.
 *
 * @see com.rubik.builder.result.ResultsBuildable
 *
 * @since 1.6
 */
@RInvariant
inline fun <reified R1, reified R2> RouterBuildable.result(
    noinline onReceive: (R1, R2) -> Unit
) =
    receiveResults { results ->
        onReceive(
            results.toTypeOfT(0),
            results.toTypeOfT(1)
        )
    }

/**
 * The result callback of RouterBuildable, with 3 parameter.
 *
 * @see com.rubik.builder.result.ResultsBuildable
 *
 * @since 1.6
 */
@RInvariant
inline fun <reified R1, reified R2, reified R3> RouterBuildable.result(
    noinline onReceive: (R1, R2, R3) -> Unit
) =
    receiveResults { results ->
        onReceive(
            results.toTypeOfT(0),
            results.toTypeOfT(1),
            results.toTypeOfT(2)
        )
    }

/**
 * The result callback of RouterBuildable, with 4 parameter.
 *
 * @see com.rubik.builder.result.ResultsBuildable
 *
 * @since 1.6
 */
@RInvariant
inline fun <reified R1, reified R2, reified R3, reified R4> RouterBuildable.result(
    noinline onReceive: (R1, R2, R3, R4) -> Unit
) =
    receiveResults { results ->
        onReceive(
            results.toTypeOfT(0),
            results.toTypeOfT(1),
            results.toTypeOfT(2),
            results.toTypeOfT(3)
        )
    }

/**
 * The result callback of RouterBuildable, with 5 parameter.
 *
 * @see com.rubik.builder.result.ResultsBuildable
 *
 * @since 1.6
 */
@RInvariant
inline fun <reified R1, reified R2, reified R3, reified R4, reified R5> RouterBuildable.result(
    noinline onReceive: (R1, R2, R3, R4, R5) -> Unit
) =
    receiveResults { results ->
        onReceive(
            results.toTypeOfT(0),
            results.toTypeOfT(1),
            results.toTypeOfT(2),
            results.toTypeOfT(3),
            results.toTypeOfT(4)
        )
    }

/**
 * The result callback of RouterBuildable, with 6 parameter.
 *
 * @see com.rubik.builder.result.ResultsBuildable
 *
 * @since 1.6
 */
@RInvariant
inline fun <reified R1, reified R2, reified R3, reified R4, reified R5, reified R6> RouterBuildable.result(
    noinline onReceive: (R1, R2, R3, R4, R5, R6) -> Unit
) =
    receiveResults { results ->
        onReceive(
            results.toTypeOfT(0),
            results.toTypeOfT(1),
            results.toTypeOfT(2),
            results.toTypeOfT(3),
            results.toTypeOfT(4),
            results.toTypeOfT(5)
        )
    }