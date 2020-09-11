//----------------------------------------------------------------------------
//
//  $Workfile: Red52Path60InPerSec.java
//
//  $Revision: X$
//
//  Project:    Paths
//
//                            Copyright (c) 2018
//                               Cedarcrest High School
//                            All Rights Reserved

//
//  Modification History:
//  $Log:
//  $
//
//----------------------------------------------------------------------------
//
//
//----------------------------------------------------------------------------
//    Parameters Used
//----------------------------------------------------------------------------
//   Time Slice= 0.020000
//   Max Vel   = 60.000000
//   Max Accel = 100.000000
//   Max Jerk  =  1000.000000
package frc.robot.paths;

public class Red52Path60InPerSec extends Path {
    public Red52Path60InPerSec() {
       kSpeed = 6.000000;
       kNumPoints = 249;
       kPoints = new double[][]{
  {0.400000, 0.400000, 359.996568, 16.003767, 50.750000, 16.002929, 36.750000},
  {0.900175, 0.699825, 359.980169, 16.021771, 50.749996, 16.016925, 36.749997},
  {2.025228, 1.574772, 359.943298, 16.062275, 50.749969, 16.048421, 36.749976},
  {3.599840, 2.800159, 359.877844, 16.134272, 50.749857, 16.104424, 36.749889},
  {5.623296, 4.376703, 359.775808, 16.246738, 50.749517, 16.191957, 36.749624},
  {7.869616, 6.130380, 359.633450, 16.404128, 50.748705, 16.314563, 36.748992},
  {10.112896, 7.887097, 359.451266, 16.606379, 50.747090, 16.472300, 36.747732},
  {12.352206, 9.647780, 359.229905, 16.853407, 50.744246, 16.665243, 36.745511},
  {14.586578, 11.413400, 358.970177, 17.145103, 50.739663, 16.893483, 36.741924},
  {16.814994, 13.184973, 358.673055, 17.481331, 50.732746, 17.157127, 36.736500},
  {19.036381, 14.963571, 358.339691, 17.861930, 50.722820, 17.456296, 36.728698},
  {21.249613, 16.750321, 357.971418, 18.286702, 50.709139, 17.791129, 36.717913},
  {23.453507, 18.546407, 357.569766, 18.755416, 50.690888, 18.161776, 36.703480},
  {25.646823, 20.353067, 357.136465, 19.267805, 50.667196, 18.568403, 36.684677},
  {27.828274, 22.171590, 356.673458, 19.823559, 50.637139, 19.011188, 36.660729},
  {29.996528, 24.003307, 356.182905, 20.422323, 50.599756, 19.490321, 36.630813},
  {32.150219, 25.849586, 355.667189, 21.063702, 50.554054, 20.006005, 36.594065},
  {34.287960, 27.711812, 355.128922, 21.747249, 50.499021, 20.558453, 36.549585},
  {36.408359, 29.591380, 354.570942, 22.472475, 50.433642, 21.147890, 36.496445},
  {38.510028, 31.489679, 353.996317, 23.238844, 50.356909, 21.774550, 36.433697},
  {40.591606, 33.408070, 353.408334, 24.045775, 50.267835, 22.438677, 36.360382},
  {42.651772, 35.347874, 352.810499, 24.892646, 50.165467, 23.140525, 36.275540},
  {44.689262, 37.310358, 352.206524, 25.778798, 50.048907, 23.880359, 36.178221},
  {46.702882, 39.296717, 351.600319, 26.703540, 49.917317, 24.658454, 36.067493},
  {48.691520, 41.308063, 350.995972, 27.666154, 49.769944, 25.475100, 35.942461},
  {50.654152, 43.345421, 350.397741, 28.665905, 49.606126, 26.330598, 35.802273},
  {52.589847, 45.409724, 349.810038, 29.702043, 49.425310, 27.225270, 35.646137},
  {54.497760, 47.501816, 349.237409, 30.773818, 49.227067, 28.159459, 35.473335},
  {56.377131, 49.622458, 348.684530, 31.880485, 49.011103, 29.133532, 35.283239},
  {58.227265, 51.772345, 348.156185, 33.021313, 48.777271, 30.147890, 35.075321},
  {59.837353, 53.762290, 347.658933, 34.191488, 48.526486, 31.199260, 34.849989},
  {61.002027, 55.397663, 347.200209, 35.382265, 48.260872, 32.280635, 34.608770},
  {61.729636, 56.670107, 346.786080, 36.585161, 47.982917, 33.384938, 34.353589},
  {62.028805, 57.570993, 346.421202, 37.791960, 47.695402, 34.505006, 34.086731},
  {61.908253, 58.091599, 346.108804, 38.994697, 47.401331, 35.633593, 33.810784},
  {61.581368, 58.418530, 345.849922, 40.189610, 47.102853, 36.767133, 33.527631},
  {61.255979, 58.743956, 345.644310, 41.376996, 46.801136, 37.905825, 33.238283},
  {60.931850, 59.068114, 345.491761, 42.557155, 46.497325, 39.049886, 32.943763},
  {60.608697, 59.391288, 345.392115, 43.730387, 46.192543, 40.199551, 32.645100},
  {60.286197, 59.713800, 345.345264, 44.896987, 45.887890, 41.355075, 32.343339},
  {59.964008, 60.035992, 345.351156, 46.057244, 45.584450, 42.516725, 32.039535},
  {59.641775, 60.358220, 345.409798, 47.211437, 45.283291, 43.684782, 31.734759},
  {59.319144, 60.680837, 345.521254, 48.359828, 44.985464, 44.859536, 31.430098},
  {58.995777, 61.004182, 345.685644, 49.502669, 44.692010, 46.041284, 31.126657},
  {58.671359, 61.328568, 345.903139, 50.640192, 44.403958, 47.230326, 30.825563},
  {58.345615, 61.654273, 346.173957, 51.772609, 44.122328, 48.426961, 30.527967},
  {58.018321, 61.981519, 346.498350, 52.900113, 43.848129, 49.631486, 30.235044},
  {57.689319, 62.310463, 346.876596, 54.022874, 43.582364, 50.844186, 29.947997},
  {57.358531, 62.641184, 347.308988, 55.141038, 43.326025, 52.065333, 29.668058},
  {57.025976, 62.973663, 347.795815, 56.254727, 43.080097, 53.295180, 29.396490},
  {56.691785, 63.307769, 348.337342, 57.364039, 42.845554, 54.533953, 29.134587},
  {56.356219, 63.643240, 348.933796, 58.469046, 42.623362, 55.781842, 28.883677},
  {56.019684, 63.979670, 349.585333, 59.569794, 42.414470, 57.039001, 28.645117},
  {55.682750, 64.316489, 350.292019, 60.666306, 42.219818, 58.305532, 28.420298},
  {55.346166, 64.652950, 351.053795, 61.758579, 42.040323, 59.581480, 28.210637},
  {55.010873, 64.988111, 351.870450, 62.846590, 41.876887, 60.866824, 28.017575},
  {54.678015, 65.320829, 352.741584, 63.930292, 41.730385, 62.161467, 27.842576},
  {54.348946, 65.649750, 353.666578, 65.009623, 41.601667, 63.465226, 27.687112},
  {54.025230, 65.973313, 354.644553, 66.084502, 41.491550, 64.777824, 27.552663},
  {53.708636, 66.289748, 355.674345, 67.154836, 41.400819, 66.098883, 27.440699},
  {53.401122, 66.597101, 356.754467, 68.220522, 41.330219, 67.427913, 27.352674},
  {53.104811, 66.893249, 357.883084, 69.281452, 41.280452, 68.764309, 27.290007},
  {52.821954, 67.175943, 359.057994, 70.337513, 41.252176, 70.107348, 27.254069},
  {52.554888, 67.442850, 0.276614, 71.388592, 41.245999, 71.456181, 27.246162},
  {52.305973, 67.691611, 1.535971, 72.434582, 41.262478, 72.809845, 27.267508},
  {52.077534, 67.919905, 2.832714, 73.475378, 41.302114, 74.167259, 27.319221},
  {51.871781, 68.125523, 4.163131, 74.510884, 41.365355, 75.527234, 27.402296},
  {51.690741, 68.306441, 5.523176, 75.541012, 41.452591, 76.888489, 27.517588},
  {51.536178, 68.460899, 6.908517, 76.565680, 41.564151, 78.249662, 27.665799},
  {51.409524, 68.587464, 8.314586, 77.584816, 41.700310, 79.609329, 27.847464},
  {51.311824, 68.685096, 9.736644, 78.598349, 41.861278, 80.966026, 28.062941},
  {61.035914, 58.842628, 9.557121, 79.801284, 42.068888, 82.125715, 28.263201},
  {63.264929, 56.734636, 9.022607, 81.049990, 42.273127, 83.245528, 28.446356},
  {63.202430, 56.797151, 8.498326, 82.299294, 42.465629, 84.368221, 28.619346},
  {63.138430, 56.861168, 7.984523, 83.549025, 42.646622, 85.493703, 28.782343},
  {63.073037, 56.926577, 7.481427, 84.799016, 42.816341, 86.621883, 28.935521},
  {63.006359, 56.993272, 6.989247, 86.049112, 42.975028, 87.752675, 29.079062},
  {62.938501, 57.061146, 6.508178, 87.299162, 43.122933, 88.885993, 29.213154},
  {62.869565, 57.130099, 6.038395, 88.549026, 43.260310, 90.021755, 29.337987},
  {62.799649, 57.200031, 5.580059, 89.798571, 43.387417, 91.159882, 29.453759},
  {62.728848, 57.270848, 5.133314, 91.047671, 43.504519, 92.300299, 29.560670},
  {62.657254, 57.342457, 4.698291, 92.296208, 43.611882, 93.442931, 29.658925},
  {62.584955, 57.414772, 4.275105, 93.544073, 43.709779, 94.587710, 29.748733},
  {62.512032, 57.487711, 3.863858, 94.791163, 43.798483, 95.734566, 29.830305},
  {62.438564, 57.561194, 3.464639, 96.037383, 43.878271, 96.883438, 29.903859},
  {62.364624, 57.635148, 3.077525, 97.282645, 43.949420, 98.034264, 29.969611},
  {62.290282, 57.709503, 2.702583, 98.526867, 44.012212, 99.186987, 30.027783},
  {62.215603, 57.784197, 2.339867, 99.769975, 44.066927, 100.341554, 30.078600},
  {62.140645, 57.859168, 1.989423, 101.011902, 44.113848, 101.497912, 30.122287},
  {62.065464, 57.934362, 1.651287, 102.252586, 44.153258, 102.656015, 30.159072},
  {61.990110, 58.009729, 1.325488, 103.491970, 44.185439, 103.815819, 30.189186},
  {61.914629, 58.085221, 1.012046, 104.730005, 44.210676, 104.977282, 30.212860},
  {61.839063, 58.160799, 0.710976, 105.966647, 44.229250, 106.140367, 30.230328},
  {61.763449, 58.236424, 0.422284, 107.201856, 44.241445, 107.305039, 30.241825},
  {61.687821, 58.312063, 0.145975, 108.435597, 44.247541, 108.471266, 30.247587},
  {61.612207, 58.387686, 359.882044, 109.667842, 44.247821, 109.639019, 30.247850},
  {61.536634, 58.463270, 359.630485, 110.898563, 44.242564, 110.808274, 30.242855},
  {61.461122, 58.538790, 359.391289, 112.127740, 44.232049, 111.979007, 30.232839},
  {61.385691, 58.614230, 359.164442, 113.355356, 44.216554, 113.151198, 30.218043},
  {61.310355, 58.689575, 358.949928, 114.581397, 44.196356, 114.324831, 30.198707},
  {61.235127, 58.764811, 358.747729, 115.805852, 44.171730, 115.499889, 30.175073},
  {61.160015, 58.839930, 358.557828, 117.028714, 44.142949, 116.676362, 30.147384},
  {61.085026, 58.914926, 358.380203, 118.249978, 44.110287, 117.854239, 30.115881},
  {61.010163, 58.989796, 358.214833, 119.469642, 44.074014, 119.033514, 30.080809},
  {60.935428, 59.064536, 358.061699, 120.687706, 44.034400, 120.214180, 30.042410},
  {60.860820, 59.139149, 357.920778, 121.904174, 43.991712, 121.396236, 30.000929},
  {60.786337, 59.213637, 357.792051, 123.119049, 43.946218, 122.579679, 29.956612},
  {60.711974, 59.288005, 357.675497, 124.332338, 43.898182, 123.764511, 29.909702},
  {60.637724, 59.362259, 357.571099, 125.544048, 43.847869, 124.950734, 29.860447},
  {60.563580, 59.436407, 357.478839, 126.754189, 43.795540, 126.138352, 29.809092},
  {60.489532, 59.510458, 357.398700, 127.962770, 43.741457, 127.327371, 29.755884},
  {60.415571, 59.584422, 357.330670, 129.169803, 43.685880, 128.517798, 29.701070},
  {60.341685, 59.658311, 357.274735, 130.375298, 43.629066, 129.709642, 29.644900},
  {60.267861, 59.732136, 357.230885, 131.579269, 43.571273, 130.902910, 29.587620},
  {60.194088, 59.805911, 357.199113, 132.781728, 43.512757, 132.097615, 29.529482},
  {60.120351, 59.879649, 357.179411, 133.982688, 43.453774, 133.293766, 29.470735},
  {60.046636, 59.953364, 357.171777, 135.182160, 43.394577, 134.491376, 29.411629},
  {59.972931, 60.027069, 357.176208, 136.380159, 43.335418, 135.690456, 29.352418},
  {59.899220, 60.100780, 357.192706, 137.576697, 43.276552, 136.891020, 29.293353},
  {59.825489, 60.174509, 357.221273, 138.771784, 43.218227, 138.093079, 29.234688},
  {59.751726, 60.248272, 357.261916, 139.965433, 43.160695, 139.296647, 29.176679},
  {59.677915, 60.322081, 357.314642, 141.157654, 43.104206, 140.501737, 29.119580},
  {59.604044, 60.395949, 357.379460, 142.348456, 43.049008, 141.708360, 29.063649},
  {59.530101, 60.469890, 357.456383, 143.537848, 42.995350, 142.916529, 29.009144},
  {59.456075, 60.543913, 357.545424, 144.725838, 42.943479, 144.126255, 28.956324},
  {59.381954, 60.618031, 357.646598, 145.912431, 42.893642, 145.337548, 28.905450},
  {59.307729, 60.692252, 357.759923, 147.097632, 42.846085, 146.550417, 28.856784},
  {59.233392, 60.766584, 357.885416, 148.281443, 42.801056, 147.764870, 28.810589},
  {59.158936, 60.841035, 358.023098, 149.463867, 42.758798, 148.980915, 28.767131},
  {59.084356, 60.915609, 358.172988, 150.644903, 42.719557, 150.198555, 28.726674},
  {59.009650, 60.990310, 358.335108, 151.824547, 42.683578, 151.417794, 28.689488},
  {58.934815, 61.065139, 358.509477, 153.002796, 42.651104, 152.638633, 28.655841},
  {58.859852, 61.140095, 358.696118, 154.179642, 42.622379, 153.861071, 28.626004},
  {58.784765, 61.215175, 358.895050, 155.355077, 42.597645, 155.085104, 28.600248},
  {58.709559, 61.290373, 359.106292, 156.529090, 42.577144, 156.310725, 28.578847},
  {58.634242, 61.365682, 359.329864, 157.701665, 42.561120, 157.537924, 28.562077},
  {58.558826, 61.441089, 359.565780, 158.872787, 42.549811, 158.766688, 28.550213},
  {58.483325, 61.516581, 359.814056, 160.042436, 42.543459, 159.997002, 28.543533},
  {58.407757, 61.592140, 0.074701, 161.210591, 42.542304, 161.228844, 28.542316},
  {58.332141, 61.667745, 0.347725, 162.377226, 42.546583, 162.462190, 28.546841},
  {58.256504, 61.743372, 0.633129, 163.542313, 42.556534, 163.697013, 28.557389},
  {58.180872, 61.818993, 0.930913, 164.705823, 42.572394, 164.933278, 28.574242},
  {58.105279, 61.894575, 1.241072, 165.867720, 42.594398, 166.170947, 28.597682},
  {58.029759, 61.970082, 1.563592, 167.027968, 42.622779, 167.409978, 28.627992},
  {57.954355, 62.045474, 1.898455, 168.186527, 42.657771, 168.650322, 28.665455},
  {57.879111, 62.120706, 2.245635, 169.343353, 42.699603, 169.891925, 28.710355},
  {57.804075, 62.195728, 2.605097, 170.498400, 42.748505, 171.134726, 28.762973},
  {57.729302, 62.270487, 2.976799, 171.651617, 42.804703, 172.378659, 28.823594},
  {57.654851, 62.344925, 3.360688, 172.802952, 42.868421, 173.623653, 28.892497},
  {57.580783, 62.418978, 3.756700, 173.952349, 42.939882, 174.869626, 28.969964},
  {57.507168, 62.492578, 4.164762, 175.099747, 43.019305, 176.116494, 29.056274},
  {57.434078, 62.565653, 4.584788, 176.245083, 43.106905, 177.364163, 29.151703},
  {57.361590, 62.638126, 5.016680, 177.388292, 43.202897, 178.612532, 29.256527},
  {57.289785, 62.709916, 5.460325, 178.529304, 43.307490, 179.861494, 29.371018},
  {57.218749, 62.780935, 5.915597, 179.668046, 43.420890, 181.110933, 29.495443},
  {57.148573, 62.851095, 6.382356, 180.804444, 43.543298, 182.360725, 29.630068},
  {57.079350, 62.920301, 6.860446, 181.938419, 43.674913, 183.610739, 29.775153},
  {57.011180, 62.988456, 7.349694, 183.069889, 43.815927, 184.860837, 29.930953},
  {56.944162, 63.055457, 7.849912, 184.198771, 43.966529, 186.110871, 30.097720},
  {56.878401, 63.121201, 8.360894, 185.324977, 44.126902, 187.360686, 30.275696},
  {56.814004, 63.185582, 8.882416, 186.448420, 44.297222, 188.610121, 30.465120},
  {56.751079, 63.248490, 9.414238, 187.569007, 44.477663, 189.859003, 30.666221},
  {56.689737, 63.309815, 9.956101, 188.686645, 44.668389, 191.107155, 30.879222},
  {56.630090, 63.369447, 10.507727, 189.801238, 44.869560, 192.354391, 31.104335},
  {56.572248, 63.427273, 11.068821, 190.912688, 45.081328, 193.600519, 31.341766},
  {56.516322, 63.483182, 11.639069, 192.020897, 45.303839, 194.845338, 31.591709},
  {56.462424, 63.537066, 12.218138, 193.125763, 45.537232, 196.088642, 31.854347},
  {56.410660, 63.588814, 12.805681, 194.227186, 45.781637, 197.330218, 32.129853},
  {56.361136, 63.638323, 13.401329, 195.325061, 46.037177, 198.569848, 32.418390},
  {56.313955, 63.685490, 14.004701, 196.419285, 46.303968, 199.807306, 32.720105},
  {56.269215, 63.730217, 14.615395, 197.509755, 46.582116, 201.042366, 33.035136},
  {59.785036, 60.142281, 14.644636, 198.665714, 46.887826, 202.205238, 33.342651},
  {65.732367, 54.266291, 13.706114, 199.940336, 47.209750, 203.257522, 33.608416},
  {65.652897, 54.345798, 12.780606, 201.218480, 47.510532, 204.315538, 33.857392},
  {65.564591, 54.434145, 11.869557, 202.499555, 47.790412, 205.379135, 34.089754},
  {65.467844, 54.530936, 10.974349, 203.782987, 48.049679, 206.448160, 34.305704},
  {65.363096, 54.635729, 10.096294, 205.068217, 48.288671, 207.522459, 34.505467},
  {65.250825, 54.748049, 9.236622, 206.354710, 48.507766, 208.601880, 34.689292},
  {65.131534, 54.867391, 8.396483, 207.641955, 48.707388, 209.686267, 34.857449},
  {65.005746, 54.993231, 7.576940, 208.929464, 48.887994, 210.775468, 35.010232},
  {64.873997, 55.125033, 6.778970, 210.216780, 49.050078, 211.869333, 35.147953},
  {64.736827, 55.262257, 6.003460, 211.503474, 49.194163, 212.967714, 35.270945},
  {64.594770, 55.404368, 5.251210, 212.789148, 49.320803, 214.070465, 35.379561},
  {64.448352, 55.550840, 4.522934, 214.073432, 49.430570, 215.177446, 35.474168},
  {64.298084, 55.701162, 3.819262, 215.355991, 49.524062, 216.288522, 35.555154},
  {64.144454, 55.854845, 3.140744, 216.636517, 49.601889, 217.403562, 35.622917},
  {63.987927, 56.011424, 2.487855, 217.914735, 49.664679, 218.522441, 35.677875},
  {63.828939, 56.170462, 1.860997, 219.190396, 49.713069, 219.645043, 35.720453},
  {63.667897, 56.331554, 1.260507, 220.463283, 49.747705, 220.771258, 35.751093},
  {63.505175, 56.494323, 0.686658, 221.733204, 49.769239, 221.900982, 35.770244},
  {63.341115, 56.658430, 0.139671, 222.999993, 49.778327, 223.034121, 35.778369},
  {63.176022, 56.823566, 359.619714, 224.263511, 49.775627, 224.170590, 35.775935},
  {63.010172, 56.989458, 359.126910, 225.523638, 49.761796, 225.310310, 35.763421},
  {62.843805, 57.155864, 358.661344, 226.780279, 49.737490, 226.453214, 35.741311},
  {62.677131, 57.322576, 358.223067, 228.033357, 49.703365, 227.599240, 35.710097},
  {62.510328, 57.489414, 357.812099, 229.282814, 49.660069, 228.748339, 35.670275},
  {62.343546, 57.656230, 357.428436, 230.528608, 49.608249, 229.900467, 35.622347},
  {62.176905, 57.822901, 357.072055, 231.770712, 49.548544, 231.055591, 35.566820},
  {62.010503, 57.989332, 356.742917, 233.009113, 49.481589, 232.213686, 35.504204},
  {61.844412, 58.155449, 356.440971, 234.243811, 49.408013, 233.374736, 35.435013},
  {61.678682, 58.321203, 356.166158, 235.474815, 49.328437, 234.538730, 35.359767},
  {61.513343, 58.486563, 355.918412, 236.702145, 49.243476, 235.705668, 35.278984},
  {61.348409, 58.651517, 355.697668, 237.925827, 49.153740, 236.875557, 35.193191},
  {61.183876, 58.816067, 355.503861, 239.145896, 49.059830, 238.048409, 35.102914},
  {61.019726, 58.980232, 355.336926, 240.362391, 48.962342, 239.224244, 35.008682},
  {60.855930, 59.144040, 355.196806, 241.575355, 48.861865, 240.403087, 34.911031},
  {60.692447, 59.307533, 355.083449, 242.784836, 48.758983, 241.584970, 34.810494},
  {60.529229, 59.470760, 354.996812, 243.990883, 48.654271, 242.769927, 34.707614},
  {60.366219, 59.633775, 354.936861, 245.193548, 48.548304, 243.958000, 34.602931},
  {60.203357, 59.796641, 354.903570, 246.392882, 48.441646, 245.149231, 34.496993},
  {60.040577, 59.959422, 354.896928, 247.588936, 48.334859, 246.343668, 34.390351},
  {59.877814, 60.122186, 354.916930, 248.781760, 48.228501, 247.541360, 34.283559},
  {59.714998, 60.284998, 354.963585, 249.971402, 48.123124, 248.742358, 34.177177},
  {59.400426, 60.293588, 355.036691, 251.154886, 48.019539, 249.943637, 34.072035},
  {58.737994, 59.944058, 355.135409, 252.325324, 47.918865, 251.138108, 33.969295},
  {57.685085, 59.185005, 355.258179, 253.474971, 47.822219, 252.317648, 33.870136},
  {56.245827, 58.012299, 355.402767, 254.596150, 47.730601, 253.474039, 33.775642},
  {54.424184, 56.421980, 355.566289, 255.681252, 47.644869, 254.598973, 33.686765},
  {52.373541, 54.566640, 355.745797, 256.725712, 47.565497, 255.687169, 33.604070},
  {50.291053, 52.649122, 355.938807, 257.728884, 47.492543, 256.737378, 33.527698},
  {48.222448, 50.717723, 356.143048, 258.691029, 47.425928, 257.749310, 33.457636},
  {46.167232, 48.772937, 356.356328, 259.612395, 47.365510, 258.722678, 33.393809},
  {44.124911, 46.815257, 356.576536, 260.493214, 47.311098, 259.657202, 33.336081},
  {42.094987, 44.845181, 356.801643, 261.333708, 47.262457, 260.552607, 33.284263},
  {40.076960, 42.863209, 357.029701, 262.134085, 47.219314, 261.408629, 33.238122},
  {38.070323, 40.869848, 357.258845, 262.894545, 47.181366, 262.225010, 33.197385},
  {36.074561, 38.865613, 357.487296, 263.615278, 47.148286, 263.001505, 33.161747},
  {34.089153, 36.851025, 357.713358, 264.296462, 47.119730, 263.737879, 33.130878},
  {32.113571, 34.826611, 357.935424, 264.938271, 47.095340, 264.433909, 33.104428},
  {30.147279, 32.792908, 358.151972, 265.540864, 47.074750, 265.089384, 33.082032},
  {28.189734, 30.750457, 358.361570, 266.104398, 47.057594, 265.704109, 33.063317},
  {26.240391, 28.699805, 358.562876, 266.629017, 47.043505, 266.277898, 33.047909},
  {24.298695, 26.641505, 358.754637, 267.114857, 47.032126, 266.810582, 33.035433},
  {22.364092, 24.576113, 358.935694, 267.562048, 47.023109, 267.302004, 33.025524},
  {20.436022, 22.504186, 359.104975, 267.970709, 47.016119, 267.752022, 33.017827},
  {18.513927, 20.426285, 359.261504, 268.340950, 47.010839, 268.160506, 33.012002},
  {16.597244, 18.342971, 359.404394, 268.672872, 47.006973, 268.527341, 33.007730},
  {14.685412, 16.254805, 359.532851, 268.966568, 47.004248, 268.852423, 33.004714},
  {12.777872, 14.162348, 359.646171, 269.222119, 47.002417, 269.135663, 33.002684},
  {10.874064, 12.066157, 359.743746, 269.439597, 47.001259, 269.376982, 33.001399},
  {8.973430, 9.966792, 359.825053, 269.619064, 47.000583, 269.576317, 33.000649},
  {7.075414, 7.864809, 359.889666, 269.760572, 47.000231, 269.733613, 33.000257},
  {5.179461, 5.760763, 359.937246, 269.864161, 47.000074, 269.848828, 33.000083},
  {3.429832, 3.816369, 359.968885, 269.932758, 47.000018, 269.925155, 33.000020},
  {2.015225, 2.242932, 359.987523, 269.973062, 47.000003, 269.970014, 33.000003},
  {0.979631, 1.090481, 359.996596, 269.992655, 47.000000, 269.991823, 33.000000},
  {0.322761, 0.359306, 359.999588, 269.999110, 47.000000, 269.999009, 33.000000},
  {0.044492, 0.049530, 360.000000, 270.000000, 47.000000, 270.000000, 33.000000},
  {0.000000, 0.000000, 360.000000, 270.000000, 47.000000, 270.000000, 33.000000}};
}}