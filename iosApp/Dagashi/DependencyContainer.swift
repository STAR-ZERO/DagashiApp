//
//  DependencyContainer.swift
//  Dagashi
//
//  Created by Kenji Abe on 2022/04/23.
//

import shared

class DependencyContainer {
    static let shared = DependencyContainer()
    
    let milestoneRepository: MilestoneRepository
    let issueRepository: IssueRepository
    
    private init() {
        let dagashiAPI = DagashiAPI.Companion().create(debug: debug)
        let dagashiDb = DatabaseFactory.shared.createDatabase(driverFactory: DatabaseDriverFactory())
        self.milestoneRepository = MilestoneRepository(dagashiAPI: dagashiAPI, dagashiDb: dagashiDb)
        self.issueRepository = IssueRepository(dagashiAPI: dagashiAPI)
    }
}
