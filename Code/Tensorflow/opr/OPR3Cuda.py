import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns
import numpy as np
from datetime import datetime

eventData = pd.read_csv('PNWDistrictWestValleyEvent2020.csv')

startTime = datetime.now()
for loop in range(30000):
    if 0 == loop % 1000:
        print(".")

    fullTeamList = np.concatenate((eventData['Team1'], eventData['Team2'], eventData['Team3']))
    fullTeamList = np.sort(fullTeamList)
    teamList = np.unique(fullTeamList)
#    print(teamList)

    numberOfMatchs = eventData['Match'].count()
#    print(numberOfMatchs)

    Teams = np.zeros((numberOfMatchs,teamList.shape[0]))
    TeamsPrime = np.zeros((teamList.shape[0],numberOfMatchs))
#    print(Teams.shape)

    Scores = eventData['Score']

    for row in range(numberOfMatchs):
        x = np.where(teamList == eventData['Team1'][row])
        Teams[row][x[0][0]] = 1
        TeamsPrime[x[0][0]][row] = 1
        x = np.where(teamList == eventData['Team2'][row])
        Teams[row][x[0][0]] = 1
        TeamsPrime[x[0][0]][row] = 1
        x = np.where(teamList == eventData['Team3'][row])
        Teams[row][x[0][0]] = 1
        TeamsPrime[x[0][0]][row] = 1
    Teams.shape

    TeamsTotal = np.matmul(TeamsPrime,Teams)
    ScoresTotal = np.matmul(TeamsPrime,Scores)
#    print(TeamsTotal.shape)
#    print(ScoresTotal.shape)

    OPRs = np.linalg.solve(TeamsTotal, ScoresTotal)
#    print(OPRs)
endTime = datetime.now()
print(endTime - startTime)

