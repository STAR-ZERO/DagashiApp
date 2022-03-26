//
//  MilestoneList.swift
//  Dagashi
//
//  Created by Kenji Abe on 2020/10/25.
//

import SwiftUI
import shared

struct MilestoneList: View {
    @ObservedObject var viewModel: ViewModel = ViewModel()

    var body: some View {
        NavigationView {
            content
                .navigationBarTitle("Android Dagashi")
                .navigationBarItems(trailing:Button(action: {
                    self.viewModel.loadMilestones(forceReload: true)
                }) {
                    Image(systemName: "arrow.clockwise")
                })
        }
    }

    @ViewBuilder
    var content: some View {
        switch viewModel.state {
        case .loading:
            ActivityIndicator()
        case .result(let milestones):
            List(milestones) { milestone in
                NavigationLink(destination: NavigationLinkLazyView(IssueList(milestone: milestone))) {
                    MilestoneRow(milestone: milestone)
                }
            }
        case .error:
            Text("Error")
        }
    }
}

extension MilestoneList {
    enum State {
        case loading
        case result([Milestone])
        case error
    }
    
    class ViewModel: ObservableObject {
        @Published var state = State.loading

        init() {
            self.loadMilestones(forceReload: false)
        }

        func loadMilestones(forceReload: Bool) {
            self.state = .loading
            dagashiSDK.getMilestone(forceReload: forceReload, completionHandler: { milestoens, error in
                if let milestoens = milestoens {
                    self.state = .result(milestoens)
                } else {
                    print("Error: \(String(describing: error))")
                    self.state = .error
                }
            })
        }
    }
}

extension Milestone: Identifiable { }
