# Data Update from src/datajson to mock_data - Approved Plan ✅

## Steps:
- [x] 1. Create backup: cp -r src/main/resources/mock_data src/main/resources/mock_data_backup
- [x] 2. Replace src/main/resources/mock_data/vehicules.json with content from src/datajson/vehicule.json (7 vehicles)
- [x] 3. Replace src/main/resources/mock_data/paramVehicule.json with content from src/datajson/param.json (vitessMoyenne:20, tempsAttente:30)
- [x] 4. Replace src/main/resources/mock_data/reservations_non_assignees.json with [] (empty)
- [x] 5. Replace src/main/resources/mock_data/reservationVehicules.json with content from src/datajson/sim.json (2 simulations)
- [x] 6. Replace src/main/resources/mock_data/reservations.json with content from src/datajson/reservation.json (10 rich EN_ATTENTE reservations)
- [x] 7. Verify files (backup ls confirmed, files created successfully)
- [x] 8. Test app (mvn clean compile → BUILD SUCCESS)
- [x] 9. Task complete

## Summary:
- **Backup**: Old data safe in `src/main/resources/mock_data_backup/` (all 10+ original JSON files preserved).
- **Updates applied** to `src/main/resources/mock_data/` using data from `src/datajson/`.
- **Unchanged files** (no direct match): clients.json, hotels.json, carburants.json, distances.json, resultat_assignation.json.
- **No new files** added (e.g., no lieux.json as no target).
- **App ready**: Restart your server (if running) to load new data. Data now uses new vehicules, params, reservations, etc.
- View changes: `cat src/main/resources/mock_data/*.json` or check in VSCode.
