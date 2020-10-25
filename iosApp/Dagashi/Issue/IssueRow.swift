//
//  IssueRow.swift
//  Dagashi
//
//  Created by Kenji Abe on 2020/10/25.
//

import SwiftUI
import shared

struct IssueRow: View {
    var issue: Issue

    var body: some View {
        VStack(alignment: .leading) {
            Text(issue.title)
                .font(.title)
            Text(issue.body)
                .font(.body)
        }
        .padding()
    }
}
