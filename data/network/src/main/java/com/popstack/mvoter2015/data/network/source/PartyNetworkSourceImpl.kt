package com.popstack.mvoter2015.data.network.source

import com.popstack.mvoter2015.data.common.party.PartyNetworkSource
import com.popstack.mvoter2015.data.network.api.MvoterApi
import com.popstack.mvoter2015.data.network.helper.executeOrThrow
import com.popstack.mvoter2015.domain.party.model.Party
import com.popstack.mvoter2015.domain.party.model.PartyId
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class PartyNetworkSourceImpl @Inject constructor(
  private val mvoterApi: MvoterApi
) : PartyNetworkSource {

  private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-mm-yyyy", Locale.ENGLISH)

  override fun getPartyList(page: Int, itemPerPage: Int): List<Party> {
    return mvoterApi.partyList(page).executeOrThrow().map { apiModel ->
      with(apiModel) {
        Party(
          id = PartyId(id),
          englishName = englishName,
          burmeseName = burmeseName,
          abbreviation = abbreviation,
          flagUrl = flagImageUrl,
          sealUrl = sealImageUrl,
          region = region,
          chairmanList = chairmans,
          leaderList = leaders,
          memberCount = memberCount,
          headquarterLocation = headQuarterLocation,
          policy = policy,
          registrationApplicationDate = LocalDate.parse(
            registrationApplicationDate,
            dateTimeFormatter
          ),
          registrationApprovalDate = LocalDate.parse(registrationApprovalDate, dateTimeFormatter),
          establishmentApprovalDate = LocalDate.parse(establishmentApprovalDate, dateTimeFormatter)
        )
      }
    }
  }

}