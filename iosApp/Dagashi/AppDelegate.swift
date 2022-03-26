//
//  AppDelegate.swift
//  Dagashi
//
//  Created by Kenji Abe on 2022/03/26.
//

import SwiftUI
import shared

#if DEBUG
let debug = true
#else
let debug = false
#endif

class AppDelegate: UIResponder, UIApplicationDelegate {
    func applicationDidFinishLaunching(_ application: UIApplication) {
        if (debug) {
            NapierProxyKt.debugBuild()
        }
    }
}
