//
//  DagashiApp.swift
//  Dagashi
//
//  Created by Kenji Abe on 2020/10/24.
//

import SwiftUI

@main
struct DagashiApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
    var body: some Scene {
        WindowGroup {
            MilestoneList()
        }
    }
}


