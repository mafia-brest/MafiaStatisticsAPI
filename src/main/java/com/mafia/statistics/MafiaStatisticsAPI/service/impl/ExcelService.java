package com.mafia.statistics.MafiaStatisticsAPI.service.impl;

import com.mafia.statistics.MafiaStatisticsAPI.dto.player.additional.StatisticsType;
import com.mafia.statistics.MafiaStatisticsAPI.dto.player.statistics.base.Statistics;
import com.mafia.statistics.MafiaStatisticsAPI.exception.InternalServerException;
import com.mafia.statistics.MafiaStatisticsAPI.exception.ResourceNotFoundException;
import com.mafia.statistics.MafiaStatisticsAPI.service.inter.IExcelService;
import com.mafia.statistics.MafiaStatisticsAPI.service.inter.IPlayerService;
import com.mafia.statistics.MafiaStatisticsAPI.service.inter.IStatisticsService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelService implements IExcelService {

    private final IStatisticsService statisticsService;

    private final IPlayerService playerService;

    @Value("${app.statistics.folder.path}")
    private String statisticsFolderPath;

    @Override
    public void uploadExcel(MultipartFile multipartFile, StatisticsType statisticsType) {
        File xlsFile = saveFileToFilesystem(multipartFile);

        Map<Integer, List<String>> table = readExcel(xlsFile);

        List<Statistics> statistics = statisticsService.parseStatistics(table, statisticsType);

        playerService.savePlayers(statistics);

        statisticsService.saveStatistics(statistics);
    }

    private File saveFileToFilesystem(MultipartFile multipartFile) {
        if (!Objects.requireNonNull(multipartFile.getContentType())
                .split("\\.")[1].equals("xls")) {
            throw new ResourceNotFoundException("File", "extension", ".xls");
        }

        File statisticsFolder = new File(statisticsFolderPath);
        statisticsFolder.mkdirs(); // create if not exists

        long unixTime = System.currentTimeMillis() / 1000L;
        File statisticsFile = new File(statisticsFolderPath + "/" + unixTime + ".xls");

        try {
            multipartFile.transferTo(statisticsFile.toPath());
        } catch (IOException e) {
            throw new InternalServerException(e.getMessage());
        }

        return statisticsFile;
    }

    private Map<Integer, List<String>> readExcel(File file) {
        Map<Integer, List<String>> data = new HashMap<>();

        Sheet sheet;
        try {
            Workbook workbook = Workbook.getWorkbook(file);
            sheet = workbook.getSheet(0);
        } catch (IOException | BiffException e) {
            throw new ResourceNotFoundException("Workbook or Sheet", "index", 0);
        }

        int rows = sheet.getRows();
        int columns = sheet.getColumns();

        for (int i = 0; i < rows; i++) {
            data.put(i, new ArrayList<>());
            for (int j = 0; j < columns; j++) {
                data.get(i).add(sheet.getCell(j, i).getContents());
            }
        }
        return data;
    }
}
