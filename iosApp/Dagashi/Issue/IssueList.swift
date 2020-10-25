//
//  IssueList.swift
//  Dagashi
//
//  Created by Kenji Abe on 2020/10/25.
//

import SwiftUI
import shared

struct IssueList: View {
    let milestone: Milestone
    @ObservedObject var viewModel: ViewModel
    
    init(milestone: Milestone) {
        self.milestone = milestone
        self.viewModel = ViewModel(milestone: milestone)
    }

    var body: some View {
        content
            .navigationBarTitle(Text(milestone.title))
    }
    
    @ViewBuilder
    var content: some View {
        switch viewModel.state {
        case .loading:
            ActivityIndicator()
        case .result(let issues):
            List(issues) { issue in
                IssueRow(issue: issue)
            }
        case .error:
            Text("Error")
        }
    }
}

extension IssueList {
    enum State {
        case loading
        case result([Issue])
        case error
    }
    
    class ViewModel: ObservableObject {
        @Published var state = State.loading
        private let api = DagashiAPI()
        private let milestone: Milestone

        init(milestone: Milestone) {
            self.milestone = milestone
            self.loadIssue()
        }

        func loadIssue() {
            self.state = .loading
            api.issues(path: milestone.path, completionHandler: { issues, error in
                if let issues = issues {
                    self.state = .result(issues)
                } else {
                    print("Error: \(String(describing: error))")
                    self.state = .error
                }
            })
        }
    }
}

extension Issue: Identifiable { }
