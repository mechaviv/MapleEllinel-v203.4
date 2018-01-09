package packet;

import client.character.AvatarLook;
import client.character.Char;
import client.character.FuncKeyMap;
import client.character.items.BodyPart;
import client.character.skills.CharacterTemporaryStat;
import client.character.skills.ForceAtomInfo;
import client.character.skills.Skill;
import client.life.*;
import connection.OutPacket;
import constants.ItemConstants;
import constants.SkillConstants;
import enums.MobStat;
import handling.OutHeader;
import loaders.ItemData;
import util.Position;
import util.Rect;

import java.util.ArrayList;
import java.util.List;

public class CField {

    public static OutPacket mobEnterField(Mob mob, boolean hasBeenInit) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_ENTER_FIELD);

        outPacket.encodeByte(mob.isSealedInsteadDead());
        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeByte(mob.getCalcDamageIndex());
        outPacket.encodeInt(mob.getTemplateId());
        ForcedMobStat fms = mob.getForcedMobStat();
        outPacket.encodeByte(fms != null);
        if(fms != null) {
            fms.encode(outPacket);
        }
        mob.getTemporaryStat().encode(outPacket);
        if(!hasBeenInit) {
            // CMob::Init
            outPacket.encodePosition(mob.getPosition());
            outPacket.encodeByte(mob.getMoveAction());
//            outPacket.encodeByte(0); // banban boss?
            outPacket.encodeShort(mob.getCurFoodhold().getId());
            outPacket.encodeShort(mob.getHomeFoothold().getId());
            byte appearType = mob.getAppearType();
            outPacket.encodeShort(appearType);
            if(appearType == -3 || appearType >= 0) {
                // init -> -2, -1 else
                outPacket.encodeInt(mob.getOption());
            }
            outPacket.encodeByte(mob.getTeamForMCarnival());
            outPacket.encodeInt(mob.getHp() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) mob.getHp());
            outPacket.encodeInt(mob.getEffectItemID());
            if(mob.isPatrolMob()) {
                outPacket.encodeInt(mob.getPatrolScopeX1());
                outPacket.encodeInt(mob.getPatrolScopeX2());
                outPacket.encodeInt(mob.getDetectX());
                outPacket.encodeInt(mob.getSenseX());
            }
            outPacket.encodeInt(mob.getPhase());
            outPacket.encodeInt(mob.getCurZoneDataType());
            outPacket.encodeInt(mob.getRefImgMobID());
            outPacket.encodeInt(0); // ?
            int ownerAID = mob.getLifeReleaseOwnerAID();
            outPacket.encodeByte(ownerAID > 0);
            if(ownerAID > 0) {
                outPacket.encodeInt(ownerAID);
                outPacket.encodeString(mob.getLifeReleaseOwnerName());
                outPacket.encodeString(mob.getLifeReleaseMobName());
            }
            outPacket.encodeInt(mob.getAfterAttack());
            outPacket.encodeInt(mob.getCurrentAction());
            outPacket.encodeByte(mob.isLeft());
            int size = 0;
            outPacket.encodeInt(size);
            for (int i = 0; i < size; i++) {
                outPacket.encodeInt(0); // ?
                outPacket.encodeInt(0); // extra time?
            }
            outPacket.encodeInt(mob.getScale());
            outPacket.encodeInt(mob.getEliteGrade());
            if(mob.getEliteGrade() >= 0) {
                size = 0;
                outPacket.encodeInt(size);
                for (int i = 0; i < size; i++) {
                    outPacket.encodeInt(0); // first skillID?
                    outPacket.encodeInt(0); // second skillID?
                }
                outPacket.encodeInt(mob.getEliteType()); // 1 normal, 3 elite boss probably
            }
            ShootingMoveStat sms = mob.getShootingMoveStat();
            outPacket.encodeByte(sms != null);
            if(sms != null) {
                sms.encode(outPacket);
            }
            size = 0;
            outPacket.encodeInt(size);
            for (int i = 0; i < size; i++) {
                outPacket.encodeInt(0); // nType
                outPacket.encodeInt(0); // key?
            }
            outPacket.encodeInt(mob.getTargetUserIdFromServer());
            outPacket.encodeInt(0);
        }
        return outPacket;
    }

    public static OutPacket mobChangeController(Mob mob, boolean hasBeenInit, boolean isController) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_CHANGE_CONTROLLER);
        outPacket.encodeByte(isController);
        outPacket.encodeInt(mob.getObjectId());
        if(isController) {
            outPacket.encodeByte(mob.getCalcDamageIndex());
            outPacket.encodeInt(mob.getTemplateId());
            ForcedMobStat fms = mob.getForcedMobStat();
            outPacket.encodeByte(fms != null);
            if(fms != null) {
                fms.encode(outPacket);
            }
            mob.getTemporaryStat().encode(outPacket);
            if(!hasBeenInit) {
                // CMob::Init
                outPacket.encodePosition(mob.getPosition());
                outPacket.encodeByte(mob.getMoveAction());
//                outPacket.encodeByte(0); // banban boss?
                outPacket.encodeShort(mob.getCurFoodhold().getId());
                outPacket.encodeShort(mob.getHomeFoothold().getId());
                byte appearType = mob.getAppearType();
                outPacket.encodeShort(appearType);
                if(appearType == -3 || appearType >= 0) {
                    // init -> -2, -1 else
                    outPacket.encodeInt(mob.getOption());
                }
                outPacket.encodeByte(mob.getTeamForMCarnival());
                outPacket.encodeInt(mob.getHp() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) mob.getHp());
                outPacket.encodeInt(mob.getEffectItemID());
                if(mob.isPatrolMob()) {
                    outPacket.encodeInt(mob.getPatrolScopeX1());
                    outPacket.encodeInt(mob.getPatrolScopeX2());
                    outPacket.encodeInt(mob.getDetectX());
                    outPacket.encodeInt(mob.getSenseX());
                }
                outPacket.encodeInt(mob.getPhase());
                outPacket.encodeInt(mob.getCurZoneDataType());
                outPacket.encodeInt(mob.getRefImgMobID());
                outPacket.encodeInt(0); // ?
                int ownerAID = mob.getLifeReleaseOwnerAID();
                outPacket.encodeByte(ownerAID > 0);
                if(ownerAID > 0) {
                    outPacket.encodeInt(ownerAID);
                    outPacket.encodeString(mob.getLifeReleaseOwnerName());
                    outPacket.encodeString(mob.getLifeReleaseMobName());
                }
                outPacket.encodeInt(mob.getAfterAttack());
                outPacket.encodeInt(mob.getCurrentAction());
                outPacket.encodeByte(mob.isLeft());
                int size = 0;
                outPacket.encodeInt(size);
                for (int i = 0; i < size; i++) {
                    outPacket.encodeInt(0); // ?
                    outPacket.encodeInt(0); // extra time?
                }
                outPacket.encodeInt(mob.getScale());
                outPacket.encodeInt(mob.getEliteGrade());
                if(mob.getEliteGrade() >= 0) {
                    size = 0;
                    outPacket.encodeInt(size);
                    for (int i = 0; i < size; i++) {
                        outPacket.encodeInt(0); // first skillID?
                        outPacket.encodeInt(0); // second skillID?
                    }
                    outPacket.encodeInt(mob.getEliteType()); // 1 normal, 3 elite boss probably
                }
                ShootingMoveStat sms = mob.getShootingMoveStat();
                outPacket.encodeByte(sms != null);
                if(sms != null) {
                    sms.encode(outPacket);
                }
                size = 0;
                outPacket.encodeInt(size);
                for (int i = 0; i < size; i++) {
                    outPacket.encodeInt(0); // nType
                    outPacket.encodeInt(0); // key?
                }
                outPacket.encodeInt(mob.getTargetUserIdFromServer());
                outPacket.encodeInt(0);
            }
        }

        return outPacket;
    }

    public static OutPacket mobLeaveField(int id, byte deadType){
        OutPacket outPacket = new OutPacket(OutHeader.MOB_LEAVE_FIELD);

        outPacket.encodeInt(id);
        outPacket.encodeByte(deadType);

        return outPacket;
    }

    public static OutPacket mobDamaged(int id, long damage, int templateID, byte type, int hp, int maxHp) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_DAMAGED);

        outPacket.encodeInt(id);
        outPacket.encodeByte(type);
        damage = damage > Integer.MAX_VALUE ? Integer.MAX_VALUE : damage;
        outPacket.encodeInt((int) damage);
        if(templateID / 10000 == 250 || templateID / 10000 == 251) {
            outPacket.encodeInt(hp);
            outPacket.encodeInt(maxHp);
        }

        return outPacket;
    }

    public static OutPacket mobHpIndicator(int objectId, byte percDamage) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_HP_INDICATOR);

        outPacket.encodeInt(objectId);
        outPacket.encodeByte(percDamage);

        return outPacket;
    }

    public static OutPacket mobCtrlAck(Mob mob, boolean nextAttackPossible, short moveID, int skillID, byte slv) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_CONTROL_ACK);

        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeShort(moveID);
        outPacket.encodeByte(nextAttackPossible);
        outPacket.encodeInt((int) mob.getMp());
        outPacket.encodeInt(skillID);
        outPacket.encodeByte(slv);
        outPacket.encodeInt(0); // nForcedAttackIdx

        return outPacket;
    }

    public static OutPacket mobStatSet(Mob mob, short delay) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_STAT_SET);
        MobTemporaryStat mts = mob.getTemporaryStat();
        boolean hasMovementStat = mts.hasNewMovementAffectingStat();
        outPacket.encodeInt(mob.getObjectId());
        mts.encode(outPacket);
        outPacket.encodeShort(delay);
        outPacket.encodeByte(1); // nCalcDamageStatIndex
        if(hasMovementStat) {
            outPacket.encodeByte(0); // ?
        }

        return outPacket;
    }

    public static OutPacket mobStatReset(Mob mob, byte byteCalcDamageStatIndex, boolean sn) {
        return mobStatReset(mob, byteCalcDamageStatIndex, sn, null);
    }

    public static OutPacket mobStatReset(Mob mob, byte calcDamageStatIndex, boolean sn, List<BurnedInfo> biList) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_STAT_RESET);
        MobTemporaryStat resetStats = mob.getTemporaryStat();
        int[] mask = resetStats.getRemovedMask();
        outPacket.encodeInt(mob.getObjectId());
        for (int i = 0; i < 3; i++) {
            outPacket.encodeIntBE(mask[i]);
        }
        if(resetStats.hasRemovedMobStat(MobStat.BurnedInfo)) {
            if(biList == null) {
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
            } else {
                int dotCount = biList.stream().mapToInt(BurnedInfo::getDotCount).sum();
                outPacket.encodeInt(dotCount);
                outPacket.encodeInt(biList.size());
                for(BurnedInfo bi : biList) {
                    outPacket.encodeInt(bi.getCharacterId());
                    outPacket.encodeInt(bi.getSuperPos());
                }
            }
            resetStats.getBurnedInfos().clear();
        }
        outPacket.encodeByte(calcDamageStatIndex);
        if(resetStats.hasRemovedMovementAffectingStat()) {
        outPacket.encodeByte(sn);
        }
        resetStats.getRemovedStatVals().clear();
        return outPacket;
    }

    public static OutPacket mobSpecialEffectBySkill(Mob mob, int skillID, int charId, short tHit) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_SPECIAL_EFFECT_BY_SKILL);

        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(skillID);
        outPacket.encodeInt(charId);
        outPacket.encodeShort(tHit);

        return outPacket;
    }

    public static OutPacket funcKeyMappedManInit(FuncKeyMap funcKeyMap) {
        OutPacket outPacket = new OutPacket(OutHeader.FUNC_KEY_MAPPED_MAN_INIT);

        funcKeyMap.encode(outPacket);

        return outPacket;
    }

    public static OutPacket affectedAreaCreated(AffectedArea aa) {
        OutPacket outPacket = new OutPacket(OutHeader.AFFECTED_AREA_CREATED);

        outPacket.encodeInt(aa.getObjectId());
        outPacket.encodeByte(aa.getMobOrigin());
        outPacket.encodeInt(aa.getCharID());
        outPacket.encodeInt(aa.getSkillID());
        outPacket.encodeByte(aa.getSlv());
        outPacket.encodeShort(aa.getDelay());
        aa.getRect().encode(outPacket);
        outPacket.encodeInt(aa.getElemAttr());
        outPacket.encodeInt(aa.getElemAttr()); // ?
        outPacket.encodePosition(aa.getPosition());
        outPacket.encodeInt(aa.getForce());
        outPacket.encodeInt(aa.getOption());
        outPacket.encodeByte(aa.getOption() != 0);
        outPacket.encodeInt(0); // ?
        if(SkillConstants.isFlipAffectAreaSkill(aa.getSkillID())) {
            outPacket.encodeByte(aa.isFlip());
        }
        outPacket.encodeInt(0); // ?

        return outPacket;
    }

    public static OutPacket affectedAreaRemoved(AffectedArea aa, boolean mistEruption) {
        OutPacket outPacket = new OutPacket(OutHeader.AFFECTED_AREA_REMOVED);

        outPacket.encodeInt(aa.getObjectId());
        if(aa.getSkillID() == 2111003) {
            outPacket.encodeByte(mistEruption);
        }

        return outPacket;
    }

    public static OutPacket summonedCreated(int charID, Summon summon) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_CREATED);

        outPacket.encodeInt(charID);
        outPacket.encodeInt(summon.getObjectId());
        outPacket.encodeInt(summon.getSkillID());
        outPacket.encodeByte(summon.getCharLevel());
        outPacket.encodeByte(summon.getSlv());
        // CSummoned::Init
        outPacket.encodePosition(summon.getPosition());
        outPacket.encodeByte(summon.getMoveAction());
        outPacket.encodeShort(summon.getCurFoothold());
        outPacket.encodeByte(summon.getMoveAbility());
        outPacket.encodeByte(summon.getAssistType());
        outPacket.encodeByte(summon.getEnterType());
        outPacket.encodeInt(summon.getObjectId());
        outPacket.encodeByte(summon.isFlyMob());
        outPacket.encodeByte(summon.isBeforeFirstAttack());
        outPacket.encodeInt(summon.getTemplateId());
        outPacket.encodeInt(summon.getBulletID());
        AvatarLook al = summon.getAvatarLook();
        outPacket.encodeByte(al != null);
        if(al != null) {
            al.encode(outPacket);
        }
        if(summon.getSkillID() == 35111002) { // Tesla Coil
            outPacket.encodeByte(summon.getTeslaCoilState());
            for(Position pos : summon.getTeslaCoilPositions()) {
                outPacket.encodePosition(pos);
            }
        }
        if(summon.getSkillID() == 42111003) { // Kishin Shoukan
            for(Position pos : summon.getKishinPositions()) {
                outPacket.encodePosition(pos);
            }
        }
        outPacket.encodeByte(summon.isJaguarActive());
        outPacket.encodeInt(summon.getSummonTerm());
        outPacket.encodeByte(summon.isAttackActive());

        return outPacket;
    }

    public static OutPacket createForceAtom(boolean byMob, int userOwner, int targetID, int forceAtomType, boolean toMob,
                                     int targets, int target, ForceAtomInfo fai, Rect rect, int arriveDir, int arriveRange,
                                     Position forcedTargetPos, int bulletID, Position pos) {
        List<Integer> integers = new ArrayList<>();
        integers.add(targets);
        List<ForceAtomInfo> forceAtomInfos = new ArrayList<>();
        forceAtomInfos.add(fai);
        return createForceAtom(byMob, userOwner, targetID, forceAtomType, toMob, integers, target, forceAtomInfos,
                rect, arriveDir, arriveRange, forcedTargetPos, bulletID, pos);
    }

    public static OutPacket createForceAtom(boolean byMob, int userOwner, int targetID, int forceAtomType, boolean toMob,
                                     List<Integer> targets, int target, List<ForceAtomInfo> faiList, Rect rect, int arriveDir, int arriveRange,
                                     Position forcedTargetPos, int bulletID, Position pos) {
        OutPacket outPacket = new OutPacket(OutHeader.CREATE_FORCE_ATOM);

        outPacket.encodeByte(byMob);
        if(byMob) {
            outPacket.encodeInt(userOwner);
        }
        outPacket.encodeInt(targetID);
        outPacket.encodeInt(forceAtomType);
        if(forceAtomType != 0 && forceAtomType != 9 && forceAtomType != 14) {
            outPacket.encodeByte(toMob);
            switch (forceAtomType) {
                case 2:
                case 3:
                case 6:
                case 7:
                case 11:
                case 12:
                case 13:
                case 17:
                case 19:
                case 20:
                case 23:
                case 24:
                case 25:
                    outPacket.encodeInt(targets.size());
                    for (int i : targets) {
                        outPacket.encodeInt(i);
                    }
                    break;
                default:
                    outPacket.encodeInt(targets.get(0));
                    break;
            }
            outPacket.encodeInt(target);
        }
        for(ForceAtomInfo fai : faiList) {
            outPacket.encodeByte(1);
            fai.encode(outPacket);
        }
        outPacket.encodeByte(0);
        switch (forceAtomType) {
            case 11:
                outPacket.encodeRectInt(rect);
                outPacket.encodeInt(bulletID);
                break;
            case 9:
            case 15:
                outPacket.encodeRectInt(rect);
                break;
            case 16:
                outPacket.encodePositionInt(pos);
                break;
            case 17:
                outPacket.encodeInt(arriveDir);
                outPacket.encodeInt(arriveRange);
                break;
            case 18:
                outPacket.encodePositionInt(forcedTargetPos);
                break;
        }

        return outPacket;
    }
    public static OutPacket finalAttackRequest(Char chr, int skillID, int finalSkillID, int delay, int mobID,
                                               int userRequestTime) {
        return finalAttackRequest(chr, skillID, finalSkillID, delay, mobID, userRequestTime, false, null);
    }

    public static OutPacket finalAttackRequest(Char chr, int skillID, int finalSkillID, int delay, int mobID,
                                               int userRequestTime, boolean left, Position base) {
        OutPacket outPacket = new OutPacket(OutHeader.FINAL_ATTACK_REQUEST);

        int wt = ItemConstants.getWeaponType(chr.getEquippedItemByBodyPart(BodyPart.WEAPON).getItemId());

        outPacket.encodeInt(skillID);
        outPacket.encodeInt(finalSkillID);
        outPacket.encodeInt(wt);
        outPacket.encodeInt(delay);
        outPacket.encodeInt(mobID);
        outPacket.encodeInt(userRequestTime);
        if(skillID == 101000102) { // Air Riot
            outPacket.encodeByte(left);
            outPacket.encodePosition(base);
        }

        return outPacket;
    }

    public static OutPacket setAmmo(int ammo) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_AMMO);

        outPacket.encodeInt(ammo);

        return outPacket;
    }
}