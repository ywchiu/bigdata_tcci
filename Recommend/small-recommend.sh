#!/bin/bash
# run distributed ALS
mahout parallelALS --input ecprocess --output als2/out --tempDir als2/tmp --numFeatures 5 \
--numIterations 2 --lambda 0.065 --numThreadsPerSolver 1

# make recommendations
mahout recommendfactorized --input als2/out/userRatings/ --output small_recommend/ \
--userFeatures als2/out/U/ --itemFeatures als2/out/M/ --numRecommendations 3 --maxRating 5 --numThreads 2


