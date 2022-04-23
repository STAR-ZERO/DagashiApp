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
        self.viewModel = ViewModel(
            issueReposiotry: DependencyContainer.shared.issueRepository,
            milestone: milestone
        )
    }

    var body: some View {
        content
            .navigationBarTitle(milestone.title)
            .navigationBarItems(trailing:Button(action: {
                self.viewModel.loadIssues()
            }) {
                Image(systemName: "arrow.clockwise")
            })
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
        private let issueRepository: IssueRepository
        private let milestone: Milestone

        init(
            issueReposiotry: IssueRepository,
            milestone: Milestone
        ) {
            self.issueRepository = issueReposiotry
            self.milestone = milestone
            self.loadIssues()
        }

        func loadIssues() {
            self.state = .loading
            self.issueRepository.getIssues(path: milestone.path, completionHandler: { issues, error in
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
