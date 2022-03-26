//
//  Sdk.swift
//  Dagashi
//
//  Created by Kenji Abe on 2021/01/16.
//

import shared

let dagashiSDK = DagashiSDK(
    dagashiAPI: DagashiAPI.Companion().create(debug: debug),
    databaseDriverFactory: DatabaseDriverFactory()
)
