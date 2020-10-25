//
//  MilestoneRow.swift
//  Dagashi
//
//  Created by Kenji Abe on 2020/10/25.
//

import SwiftUI
import shared

struct MilestoneRow: View {
    var milestone: Milestone

    var body: some View {
        VStack(alignment: .leading) {
            Text(milestone.title)
                .font(.title)
            Text(milestone.body)
                .font(.body)
        }
        .padding()
    }
}
