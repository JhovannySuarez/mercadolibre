package com.mercadolibre.reto.xmen.services.impl;

import com.mercadolibre.reto.xmen.Utils.DNAUtil;
import com.mercadolibre.reto.xmen.domain.DNAStatistic;
import com.mercadolibre.reto.xmen.dto.DNARequestDTO;
import com.mercadolibre.reto.xmen.exceptions.DNASequenceException;
import com.mercadolibre.reto.xmen.services.DNAEvaluatorService;
import com.mercadolibre.reto.xmen.services.DNAStatisticService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class DNAEvaluatorServiceImpl implements DNAEvaluatorService {

    @Autowired
    private DNAStatisticService dnaStatisticService;

    public boolean isMutant(DNARequestDTO dnaRequestDTO) throws DNASequenceException {
        String[] dna = dnaRequestDTO.getDna();
        if (dna != null) {
            UUID dnaUUID = UUID.nameUUIDFromBytes(DNAUtil.dnaToBytes(dna));
            Optional<DNAStatistic> dnaStatistic = dnaStatisticService.getDnaStatistic(dnaUUID);
            if (dnaStatistic.isPresent())
                return dnaStatistic.get().getIsMutant();
            else {
                return processDNA(dna,dnaUUID);
            }
        } else {
            throw new DNASequenceException("Nitrogenous base not valid ");
        }
    }


    private boolean processDNA(String[] dna, UUID dnaUUID) throws DNASequenceException {

        int mutantSubsequenceCount = 0;
        for (int i = 0; i < dna.length; i++) {
            String dnaSequence = dna[i];
            int j = 0;
            if (dnaSequence == null)
                continue;
            while (j < dnaSequence.length()) {

                mutantSubsequenceCount += checkHorizontalSubSequence(dnaSequence, j, mutantSubsequenceCount);

                if (dna.length - i >= 4) {
                    mutantSubsequenceCount += checkVerticalSubSequence(
                            dna[i], dna[i + 1], dna[i + 2], dna[i + 3], j, mutantSubsequenceCount);
                    mutantSubsequenceCount += checkObliquelyRightSubSequence(
                            dna[i], dna[i + 1], dna[i + 2], dna[i + 3], j, mutantSubsequenceCount);

                    mutantSubsequenceCount += checkObliquelyLeftSubSequence(
                            dna[i], dna[i + 1], dna[i + 2], dna[i + 3], j, mutantSubsequenceCount);
                }
                if (mutantSubsequenceCount == 2) {
                    saveStatistics(true, dnaUUID);
                    return true;
                }
                j++;
            }
        }
        saveStatistics(false, dnaUUID);
        return false;
    }

    /**
     * This method gets the horizontal subsequence since the current index up to 4 and evaluate it to check
     * whether the subsequence is mutant or not
     *
     * @param dnaSequence            the current dna sequence row being evaluated
     * @param currentIndex           The current index to get the subsequence to be evaluated
     * @param mutantSubsequenceCount if mutantSubsequenceCount is 2 or upper, it is not necessary to check
     * @return 1 if the evaluation of the subsequence is mutant, 0 otherwise
     * @throws DNASequenceException if the horizontal subsequence is not a valid ADN sequence
     */
    private int checkHorizontalSubSequence(@NonNull final String dnaSequence, int currentIndex, int mutantSubsequenceCount) throws DNASequenceException {
        int border = currentIndex + 4;
        if (border <= dnaSequence.length() && mutantSubsequenceCount < 2) {
            String horizontalSubSequence = getHorizontalSubSequence(dnaSequence, currentIndex);
            isValidSequence(horizontalSubSequence);
            if (isMutantSubSequence(horizontalSubSequence)) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * @param dnaSequence  dnaSequence to be evaulated.
     * @param currentIndex The current index to get the subsequence to be evaluated
     * @return the horizontal subsequence since the currentIndex until 4 positions forward
     */
    private String getHorizontalSubSequence(@NonNull final String dnaSequence, int currentIndex) {
        return dnaSequence.substring(currentIndex, currentIndex + 4);
    }

    /**
     * This method gets the vertical subsequence since the current index up to 4 rows below and evaluate it to check
     * whether the subsequence is mutant or not
     *
     * @param row1                   Row to get fist base of the subsequence
     * @param row2                   Row to get second base of the subsequence
     * @param row3                   Row to get third base of the subsequence
     * @param row4                   Row to get fourth base of the subsequence
     * @param currentIndex           The current column index to get the subsequence to be evaluated
     * @param mutantSubsequenceCount if mutantSubsequenceCount is 2 or upper, it is not necessary to check
     * @return 1 if the evaluation of the subsequence is mutant, 0 otherwise
     * @throws DNASequenceException if vertical subsequence contains a not valid ADN nitrogenous base
     */
    private int checkVerticalSubSequence(@NonNull final String row1, @NonNull final String row2,
                                         @NonNull final String row3, @NonNull final String row4, int currentIndex, int mutantSubsequenceCount) throws DNASequenceException {

        if (mutantSubsequenceCount < 2) {
            if (row1.length() >= currentIndex && row2.length() >= currentIndex && row3.length() >= currentIndex && row4.length() >= currentIndex) {
                String verticalSubSequence = getVerticalSubSequence(row1, row2, row3, row4, currentIndex);
                isValidSequence(verticalSubSequence);
                if (isMutantSubSequence(verticalSubSequence)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * @param row1         Row to get fist base of the subsequence
     * @param row2         Row to get second base of the subsequence
     * @param row3         Row to get third base of the subsequence
     * @param row4         Row to get fourth base of the subsequence
     * @param currentIndex The current index to get the subsequence to be evaluated
     * @return the vertical subsequence in the column 'currentIndex'
     */
    private String getVerticalSubSequence(@NonNull final String row1, @NonNull final String row2,
                                          @NonNull final String row3, @NonNull final String row4, int currentIndex) {
        StringBuilder subsequence = new StringBuilder();
        subsequence.append(row1.charAt(currentIndex))
                .append(row2.charAt(currentIndex))
                .append(row3.charAt(currentIndex))
                .append(row4.charAt(currentIndex));
        return subsequence.toString();
    }

    /**
     * @param row1         Row to get fist base of the subsequence
     * @param row2         Row to get second base of the subsequence
     * @param row3         Row to get third base of the subsequence
     * @param row4         Row to get fourth base of the subsequence
     * @param currentIndex The current index to get the subsequence to be evaluated
     * @return 1 if the evaluation of the subsequence is mutant, 0 otherwise
     */
    private int checkObliquelyRightSubSequence(@NonNull final String row1, @NonNull final String row2,
                                               @NonNull final String row3, @NonNull final String row4, int currentIndex,
                                               int mutantSubsequenceCount) throws DNASequenceException {
        int border = currentIndex + 4;
        if (mutantSubsequenceCount < 2) { //if mutantSubsequenceCount is 2 or upper, it is not necessary to check
            if (row1.length() >= border && row2.length() >= border && row3.length() >= border && row4.length() >= border) {
                String obliquelyRight = getObliquelyRightSubSequence(row1, row2, row3, row4, currentIndex);
                isValidSequence(obliquelyRight);
                if (isMutantSubSequence(obliquelyRight)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * @param row1         Row to get fist base of the subsequence
     * @param row2         Row to get second base of the subsequence
     * @param row3         Row to get third base of the subsequence
     * @param row4         Row to get fourth base of the subsequence
     * @param currentIndex The current index to get the subsequence to be evaluated
     * @return the Oblique right subsequence since the column 'currentIndex' until 'currentIndex' + 3
     */
    private String getObliquelyRightSubSequence(@NonNull final String row1, @NonNull final String row2,
                                                @NonNull final String row3, @NonNull final String row4, int currentIndex) {
        StringBuilder subsequence = new StringBuilder();
        subsequence.append(row1.charAt(currentIndex))
                .append(row2.charAt(currentIndex + 1))
                .append(row3.charAt(currentIndex + 2))
                .append(row4.charAt(currentIndex + 3));
        return subsequence.toString();
    }

    /**
     * @param row1         Row to get fist base of the subsequence
     * @param row2         Row to get second base of the subsequence
     * @param row3         Row to get third base of the subsequence
     * @param row4         Row to get fourth base of the subsequence
     * @param currentIndex The current index to get the subsequence to be evaluated
     * @return the Oblique left subsequence since the column 'currentIndex' until 'currentIndex' - 3
     */
    private int checkObliquelyLeftSubSequence(@NonNull final String row1, @NonNull final String row2,
                                              @NonNull final String row3, @NonNull final String row4, int currentIndex, int mutantSubsequenceCount)
            throws DNASequenceException {

        if (mutantSubsequenceCount < 2) { //if mutantSubsequenceCount is 2 or upper, it is not necessary to check
            if (currentIndex >= 3 && row1.length() >= currentIndex + 1 && row2.length() >= currentIndex && row3.length() >= currentIndex - 1 && row3.length() >= currentIndex - 2) {
                String obliquelyLeft = getObliquelyLeftSubSequence(row1, row2, row3, row4, currentIndex);
                isValidSequence(obliquelyLeft);
                if (isMutantSubSequence(obliquelyLeft)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    private String getObliquelyLeftSubSequence(@NonNull final String row1, @NonNull final String row2,
                                               @NonNull final String row3, @NonNull final String row4, int j) {
        StringBuilder subsequence = new StringBuilder();
        subsequence.append(row1.charAt(j))
                .append(row2.charAt(j - 1))
                .append(row3.charAt(j - 2))
                .append(row4.charAt(j - 3));
        return subsequence.toString();
    }

    /**
     * check whether the subSequence is mutant or not
     *
     * @param subSequence to be evaluated
     * @return true if the all nitrogenous base of the subsequence are equal false otherwise
     */
    private boolean isMutantSubSequence(String subSequence) {
        if (subSequence != null && subSequence.length() == 4) {
            return subSequence.chars().distinct().count() == 1;
        }
        return false;
    }

    /**
     * Evaluated if the all nitrogenous base of the subsequence ara valid [ATCG]
     *
     * @param dnaSequenceRow the ADN sequence to be validated
     * @throws DNASequenceException if vertical subsequence contains a not valid ADN nitrogenous base
     */
    private void isValidSequence(String dnaSequenceRow) throws DNASequenceException {
        if (dnaSequenceRow == null || !dnaSequenceRow.matches("[ATCGatcg]+")) {
            throw new DNASequenceException("Nitrogenous base not valid " + dnaSequenceRow);
        }
    }

    private void saveStatistics(boolean isMutant, UUID dnaUUID) {
        DNAStatistic dnaStatistic = DNAStatistic.builder().
                isMutant(isMutant)
                .analysisCode(dnaUUID)
                .build();
        dnaStatisticService.saveDNAStatistic(dnaStatistic);
    }
}
