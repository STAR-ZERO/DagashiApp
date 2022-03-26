//
//  NavigationLinkLazyView.swift
//  Dagashi
//
//  Created by Kenji Abe on 2022/03/26.
//

import SwiftUI

struct NavigationLinkLazyView<Content: View>: View {
    let build: () -> Content
    init(_ build: @autoclosure @escaping () -> Content) {
        self.build = build
    }
    var body: Content {
        build()
    }
}
