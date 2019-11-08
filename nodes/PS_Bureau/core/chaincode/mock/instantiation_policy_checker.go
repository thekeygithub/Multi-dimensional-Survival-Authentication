// Code generated by counterfeiter. DO NOT EDIT.
package mock

import (
	"sync"

	"github.com/hyperledger/fabric/core/common/ccprovider"
)

type InstantiationPolicyChecker struct {
	CheckInstantiationPolicyStub        func(name, version string, cd *ccprovider.ChaincodeData) error
	checkInstantiationPolicyMutex       sync.RWMutex
	checkInstantiationPolicyArgsForCall []struct {
		name    string
		version string
		cd      *ccprovider.ChaincodeData
	}
	checkInstantiationPolicyReturns struct {
		result1 error
	}
	checkInstantiationPolicyReturnsOnCall map[int]struct {
		result1 error
	}
	invocations      map[string][][]interface{}
	invocationsMutex sync.RWMutex
}

func (fake *InstantiationPolicyChecker) CheckInstantiationPolicy(name string, version string, cd *ccprovider.ChaincodeData) error {
	fake.checkInstantiationPolicyMutex.Lock()
	ret, specificReturn := fake.checkInstantiationPolicyReturnsOnCall[len(fake.checkInstantiationPolicyArgsForCall)]
	fake.checkInstantiationPolicyArgsForCall = append(fake.checkInstantiationPolicyArgsForCall, struct {
		name    string
		version string
		cd      *ccprovider.ChaincodeData
	}{name, version, cd})
	fake.recordInvocation("CheckInstantiationPolicy", []interface{}{name, version, cd})
	fake.checkInstantiationPolicyMutex.Unlock()
	if fake.CheckInstantiationPolicyStub != nil {
		return fake.CheckInstantiationPolicyStub(name, version, cd)
	}
	if specificReturn {
		return ret.result1
	}
	return fake.checkInstantiationPolicyReturns.result1
}

func (fake *InstantiationPolicyChecker) CheckInstantiationPolicyCallCount() int {
	fake.checkInstantiationPolicyMutex.RLock()
	defer fake.checkInstantiationPolicyMutex.RUnlock()
	return len(fake.checkInstantiationPolicyArgsForCall)
}

func (fake *InstantiationPolicyChecker) CheckInstantiationPolicyArgsForCall(i int) (string, string, *ccprovider.ChaincodeData) {
	fake.checkInstantiationPolicyMutex.RLock()
	defer fake.checkInstantiationPolicyMutex.RUnlock()
	return fake.checkInstantiationPolicyArgsForCall[i].name, fake.checkInstantiationPolicyArgsForCall[i].version, fake.checkInstantiationPolicyArgsForCall[i].cd
}

func (fake *InstantiationPolicyChecker) CheckInstantiationPolicyReturns(result1 error) {
	fake.CheckInstantiationPolicyStub = nil
	fake.checkInstantiationPolicyReturns = struct {
		result1 error
	}{result1}
}

func (fake *InstantiationPolicyChecker) CheckInstantiationPolicyReturnsOnCall(i int, result1 error) {
	fake.CheckInstantiationPolicyStub = nil
	if fake.checkInstantiationPolicyReturnsOnCall == nil {
		fake.checkInstantiationPolicyReturnsOnCall = make(map[int]struct {
			result1 error
		})
	}
	fake.checkInstantiationPolicyReturnsOnCall[i] = struct {
		result1 error
	}{result1}
}

func (fake *InstantiationPolicyChecker) Invocations() map[string][][]interface{} {
	fake.invocationsMutex.RLock()
	defer fake.invocationsMutex.RUnlock()
	fake.checkInstantiationPolicyMutex.RLock()
	defer fake.checkInstantiationPolicyMutex.RUnlock()
	copiedInvocations := map[string][][]interface{}{}
	for key, value := range fake.invocations {
		copiedInvocations[key] = value
	}
	return copiedInvocations
}

func (fake *InstantiationPolicyChecker) recordInvocation(key string, args []interface{}) {
	fake.invocationsMutex.Lock()
	defer fake.invocationsMutex.Unlock()
	if fake.invocations == nil {
		fake.invocations = map[string][][]interface{}{}
	}
	if fake.invocations[key] == nil {
		fake.invocations[key] = [][]interface{}{}
	}
	fake.invocations[key] = append(fake.invocations[key], args)
}
