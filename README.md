<p align="center">
  <img src="https://www.corda.net/wp-content/uploads/2016/11/fg005_corda_b.png" alt="Corda" width="500">
</p>

### Observer node Setup

    ## On Party A cmd
      Wed Feb 26 17:32:39 IST 2020>>> flow start AddName bal: 7, party: PartyB
       
        ✅   Starting
                 Requesting signature by notary service
                     Requesting signature by Notary service
                     Validating response from Notary service
            ✅   Broadcasting transaction to participants
       ➡️   Done
       Flow completed with result: SignedTransaction(id=8D26F085F22CF6B13C9877B22EB44D0FB7980B4165D4B20385485198A8BFE5CD)
       
   #####Check party A state it will say 
   
        Wed Feb 26 17:33:18 IST 2020>>> run vaultQuery contractStateType: com.template.states.NameState
        states: []
        statesMetadata: []
        totalStatesAvailable: -1
        stateTypes: "UNCONSUMED"
        otherResults: []
        
   #####PartyB ofcourse has state data:- 
   
        Wed Feb 26 17:32:19 IST 2020>>> run vaultQuery contractStateType: com.template.states.NameState
        states:
        - state:
            data: !<com.template.states.NameState>
              bal: 7
              party: "O=PartyB, L=New York, C=US"
            contract: "com.template.contracts.SendContract"
            notary: "O=Notary, L=London, C=GB"
            encumbrance: null
            constraint: !<net.corda.core.contracts.SignatureAttachmentConstraint>
              key: "aSq9DsNNvGhYxYyqA9wd2eduEAZ5AXWgJTbTEw3G5d2maAq8vtLE4kZHgCs5jcB1N31cx1hpsLeqG2ngSysVHqcXhbNts6SkRWDaV7xNcr6MtcbufGUchxredBb6"
          ref:
            txhash: "8D26F085F22CF6B13C9877B22EB44D0FB7980B4165D4B20385485198A8BFE5CD"
            index: 0
        statesMetadata:
        - ref:
            txhash: "8D26F085F22CF6B13C9877B22EB44D0FB7980B4165D4B20385485198A8BFE5CD"
            index: 0
          contractStateClassName: "com.template.states.NameState"
          recordedTime: "2020-02-26T12:03:19.099Z"
          consumedTime: null
          status: "UNCONSUMED"
          notary: "O=Notary, L=London, C=GB"
          lockId: null
          lockUpdateTime: null
          relevancyStatus: "RELEVANT"
          constraintInfo:
            constraint:
              key: "aSq9DsNNvGhYxYyqA9wd2eduEAZ5AXWgJTbTEw3G5d2maAq8vtLE4kZHgCs5jcB1N31cx1hpsLeqG2ngSysVHqcXhbNts6SkRWDaV7xNcr6MtcbufGUchxredBb6"
        totalStatesAvailable: -1
        stateTypes: "UNCONSUMED"
        otherResults: []
        
   #####PartyC also has this transaction, but why? 
   
        Wed Feb 26 17:32:21 IST 2020>>> run vaultQuery contractStateType: com.template.states.NameState
        states:
        - state:
            data: !<com.template.states.NameState>
              bal: 7
              party: "O=PartyB, L=New York, C=US"
            contract: "com.template.contracts.SendContract"
            notary: "O=Notary, L=London, C=GB"
            encumbrance: null
            constraint: !<net.corda.core.contracts.SignatureAttachmentConstraint>
              key: "aSq9DsNNvGhYxYyqA9wd2eduEAZ5AXWgJTbTEw3G5d2maAq8vtLE4kZHgCs5jcB1N31cx1hpsLeqG2ngSysVHqcXhbNts6SkRWDaV7xNcr6MtcbufGUchxredBb6"
          ref:
            txhash: "8D26F085F22CF6B13C9877B22EB44D0FB7980B4165D4B20385485198A8BFE5CD"
            index: 0
        statesMetadata:
        - ref:
            txhash: "8D26F085F22CF6B13C9877B22EB44D0FB7980B4165D4B20385485198A8BFE5CD"
            index: 0
          contractStateClassName: "com.template.states.NameState"
          recordedTime: "2020-02-26T12:03:25.856Z"
          consumedTime: null
          status: "UNCONSUMED"
          notary: "O=Notary, L=London, C=GB"
          lockId: null
          lockUpdateTime: null
          relevancyStatus: "NOT_RELEVANT"
          constraintInfo:
            constraint:
              key: "aSq9DsNNvGhYxYyqA9wd2eduEAZ5AXWgJTbTEw3G5d2maAq8vtLE4kZHgCs5jcB1N31cx1hpsLeqG2ngSysVHqcXhbNts6SkRWDaV7xNcr6MtcbufGUchxredBb6"
        totalStatesAvailable: -1
        stateTypes: "UNCONSUMED"
        otherResults: []
        
    
   ###### Here Party C just act as observer node, that will observe transaction between Party A and B.          

